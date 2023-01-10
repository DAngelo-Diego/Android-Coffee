package com.example.bookapp.data.paging_source


import androidx.paging.PagingSource.*
import com.example.bookapp.data.remote.BookApi
import com.example.bookapp.data.remote.FakeBookApi
import com.example.bookapp.domain.model.Book
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class SearchBooksSourceTest {

    private lateinit var bookApi: BookApi
    private lateinit var books: List<Book>

    @Before
    fun setup(){
        bookApi = FakeBookApi()
        books = listOf(
            Book(
                id = 1,
                name = "RUSSIA",
                image = "",
                about = "GO TO AIRPLANE",
                rating = 4.9,
                level = "hard",
                timeToLearn = "HARDER",
                tags = listOf("super hard", "impossible")
            ),
            Book(
                id = 2,
                name = "JAPAN",
                image = "",
                about = "GO TO AIRPLANE",
                rating = 4.9,
                level = "hard",
                timeToLearn = "HARDER",
                tags = listOf("super hard", "impossible")
            ),
            Book(
                id = 3,
                name = "RUMANIA",
                image = "",
                about = "GO TO AIRPLANE",
                rating = 4.9,
                level = "hard",
                timeToLearn = "HARDER",
                tags = listOf("super hard", "impossible")
            )
        )
    }

    @Test
    fun `Search api with a existing book name, expect single book result, assert LoadResultPage`() =
        runTest {
            val bookSource = SearchBooksSource(bookApi = bookApi, query = "RUSSIA")
            assertEquals<LoadResult<Int, Book>>(
                expected = LoadResult.Page(
                    data = listOf(books.first()),
                    nextKey = null,
                    prevKey = null
                ),
                actual = bookSource.load(
                    LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = true
                    )
                )
            )
        }

    @Test
    fun `Search api with a existing book name, expect multiple book result, assert LoadResultPage`() =
        runTest {
            val bookSource = SearchBooksSource(bookApi = bookApi, query = "RU")
            assertEquals<LoadResult<Int, Book>>(
                expected = LoadResult.Page(
                    data = listOf(books.first(), books[2]),
                    nextKey = null,
                    prevKey = null
                ),
                actual = bookSource.load(
                    LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = true
                    )
                )
            )
        }


    @Test
    fun `Search api with a empty book name, assert empty book list and LoadResultPage`() =
        runTest {
            val bookSource = SearchBooksSource(bookApi = bookApi, query = "")
            val loadResult = bookSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = true
                )
            )
            val result = bookApi.searchBooks("").books

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is LoadResult.Page }
        }

    @Test
    fun `Search api with a non_existing book name, assert empty book list and LoadResultPage`() =
        runTest {
            val bookSource = SearchBooksSource(bookApi = bookApi, query = "Unknown")
            val loadResult = bookSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = true
                )
            )
            val result = bookApi.searchBooks("Unknown").books

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is LoadResult.Page }
        }

}