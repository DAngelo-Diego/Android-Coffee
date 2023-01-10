package com.example.bookapp.data.remote

import com.example.bookapp.domain.model.ApiResponse
import com.example.bookapp.domain.model.Book
import java.io.IOException

class FakeBookApi2: BookApi {

    private val books: Map<Int,List<Book>> by lazy {
        mapOf(
            1 to page1,
            2 to page2,
            3 to page3
        )
    }
    private var page1 = listOf(
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
    private var page2 = listOf(
        Book(
            id = 4,
            name = "RUSSIA",
            image = "",
            about = "GO TO AIRPLANE",
            rating = 4.9,
            level = "hard",
            timeToLearn = "HARDER",
            tags = listOf("super hard", "impossible")
        ),
        Book(
            id = 5,
            name = "JAPAN",
            image = "",
            about = "GO TO AIRPLANE",
            rating = 4.9,
            level = "hard",
            timeToLearn = "HARDER",
            tags = listOf("super hard", "impossible")
        ),
        Book(
            id = 6,
            name = "RUMANIA",
            image = "",
            about = "GO TO AIRPLANE",
            rating = 4.9,
            level = "hard",
            timeToLearn = "HARDER",
            tags = listOf("super hard", "impossible")
        )
    )
    private var page3 = listOf(
        Book(
            id = 7,
            name = "RUSSIA",
            image = "",
            about = "GO TO AIRPLANE",
            rating = 4.9,
            level = "hard",
            timeToLearn = "HARDER",
            tags = listOf("super hard", "impossible")
        ),
        Book(
            id = 8,
            name = "JAPAN",
            image = "",
            about = "GO TO AIRPLANE",
            rating = 4.9,
            level = "hard",
            timeToLearn = "HARDER",
            tags = listOf("super hard", "impossible")
        ),
        Book(
            id = 9,
            name = "RUMANIA",
            image = "",
            about = "GO TO AIRPLANE",
            rating = 4.9,
            level = "hard",
            timeToLearn = "HARDER",
            tags = listOf("super hard", "impossible")
        )
    )


    fun clearData(){
        page1 = emptyList()
    }

    private var exception = false

    fun addException(){
        exception = true
    }


    override suspend fun getAllBooks(page: Int): ApiResponse {
        if (exception){
            throw IOException()
        }
        require(page in 1..3)
        return ApiResponse(
            success = true,
            message = "ok",
            prevPage = calculatePage(page = page)["prevPage"],
            nextPage = calculatePage(page = page)["nextPage"],
            books = books[page]!!
        )
    }

    override suspend fun searchBooks(name: String): ApiResponse {
        return ApiResponse(
            success = false
        )
    }


    private fun calculatePage(page: Int): Map<String, Int?> {
            if (page1.isEmpty()){
                return mapOf("prevPage" to null, "nextPage" to null)
            }
            return mapOf(
                "prevPage" to if (page in 2..3) page.minus(1) else null,
                "nextPage" to if (page in 1..2) page.plus(1) else null
            )

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