package net.chandol.springwithk8s.domain.book

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : ReactiveCrudRepository<Book, Long>
