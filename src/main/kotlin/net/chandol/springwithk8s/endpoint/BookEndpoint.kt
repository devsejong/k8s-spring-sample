package net.chandol.springwithk8s.endpoint

import net.chandol.springwithk8s.domain.book.Book
import net.chandol.springwithk8s.domain.book.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BookEndpoint(
    val bookService: BookService
) {
    @GetMapping("/api/v1/book/_search")
    suspend fun getAllBooks(): List<Book> {
        return bookService.findAllBooks()
    }
}
