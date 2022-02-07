package net.chandol.springwithk8s.domain.book

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("book")
data class Book(
    @Id
    var id: Long? = null,
    var title: String,
    var author: String
)
