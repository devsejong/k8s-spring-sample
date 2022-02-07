package net.chandol.springwithk8s.domain.book

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    suspend fun findAllBooks(): List<Book> {
        return bookRepository.findAll().asFlow().toList()
    }
}
