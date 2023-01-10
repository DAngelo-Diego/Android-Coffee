package com.example.bookapp.data.paging_source

import androidx.paging.*
import androidx.paging.RemoteMediator.*
import androidx.test.core.app.ApplicationProvider
import com.example.bookapp.data.local.BookDatabase
import com.example.bookapp.data.remote.FakeBookApi2
import com.example.bookapp.domain.model.Book
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookRemoteMediatorTest {

    private lateinit var bookApi2: FakeBookApi2
    private lateinit var bookDatabase: BookDatabase

    @Before
    fun setup(){
        bookApi2 = FakeBookApi2()
        bookDatabase = BookDatabase.create(
            context = ApplicationProvider.getApplicationContext(),
            useInMemory = true
        )
    }

    @After
    fun cleanUp(){
        bookDatabase.clearAllTables()
    }


    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnSuccessResultWhenMoreDataIsPresent() =
        runBlocking {
            val remoteMediator = BookRemoteMediator(
                bookApi = bookApi2,
                bookDatabase = bookDatabase
            )
            val pagingState = PagingState<Int,Book>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Success)
            assertFalse((result as MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadSuccessAndEndOfPaginationTrueWhenNoMoreData() =
        runBlocking {
            bookApi2.clearData()
            val remoteMediator = BookRemoteMediator(
                bookApi = bookApi2,
                bookDatabase = bookDatabase
            )
            val pagingState = PagingState<Int,Book>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Success)
            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnErrorResultWhenErrorOccurs() =
        runBlocking {
            bookApi2.addException()
            val remoteMediator = BookRemoteMediator(
                bookApi = bookApi2,
                bookDatabase = bookDatabase
            )
            val pagingState = PagingState<Int,Book>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Error)
        }

}