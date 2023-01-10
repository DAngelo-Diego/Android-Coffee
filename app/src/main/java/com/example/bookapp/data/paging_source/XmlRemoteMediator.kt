package com.example.bookapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.bookapp.data.local.BookDatabase
import com.example.bookapp.data.remote.BookApi
import com.example.bookapp.domain.model.Jetpack
import com.example.bookapp.domain.model.JetpackRemoteKeys
import com.example.bookapp.domain.model.XmlModel
import com.example.bookapp.domain.model.XmlRemoteKeys
import java.lang.Exception

@ExperimentalPagingApi
class XmlRemoteMediator(
    private val bookApi: BookApi,
    private val bookDatabase: BookDatabase
) : RemoteMediator<Int, XmlModel>() {


    private val xmlDao = bookDatabase.xmlDao()
    private val xmlRemoteKeysDao = bookDatabase.xmlRemoteKeysDao()


    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = xmlRemoteKeysDao.getRemoteKeys(xmlId = 1)?.lastUpdated ?: 0
        val cacheTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, XmlModel>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }


            val response = bookApi.getAllXmls(page = page)
            if (response.xml.isNotEmpty()) {
                bookDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        xmlDao.deleteXml()
                        xmlRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.xml.map { book ->
                        XmlRemoteKeys(
                            id = book.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    xmlRemoteKeysDao.addAllRemoteKeys(xmlRemoteKeys = keys)
                    xmlDao.addXmls(xml = response.xml)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, XmlModel>
    ): XmlRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                xmlRemoteKeysDao.getRemoteKeys(xmlId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, XmlModel>
    ): XmlRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { book ->
                xmlRemoteKeysDao.getRemoteKeys(xmlId = book.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, XmlModel>
    ): XmlRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { book ->
                xmlRemoteKeysDao.getRemoteKeys(xmlId = book.id)
            }
    }

}

