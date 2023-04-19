package dev.manley.kotlinapitemplate.domain.model

import java.util.UUID

data class Person(
    val id: UUID,
    val name: String,
    val email: String,
    val password: String
)
