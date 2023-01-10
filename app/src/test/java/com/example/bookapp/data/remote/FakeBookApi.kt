package com.example.bookapp.data.remote

import com.example.bookapp.domain.model.ApiResponse
import com.example.bookapp.domain.model.Book

class FakeBookApi : BookApi {


    private val books = listOf(
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


    override suspend fun getAllBooks(page: Int): ApiResponse {
        return ApiResponse(
            success = true
        )
    }

    override suspend fun searchBooks(name: String): ApiResponse {
        val searchedBooks = findBook(name)
        return ApiResponse(
            success = true,
            message = "ok",
            books = searchedBooks
        )
    }


    private fun findBook(name: String): List<Book> {
        val founded = mutableListOf<Book>()
        return if (name.isNotEmpty()) {
            books.forEach { book ->
                if (book.name.lowercase().contains(name.lowercase())) {
                    founded.add(book)
                }
            }
            founded
            } else {
            emptyList()
        }
    }



























    override suspend fun getAllJetpacks(page: Int): ApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchJetpack(name: String): ApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllXmls(page: Int): ApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchXml(name: String): ApiResponse {
        TODO("Not yet implemented")
    }
}