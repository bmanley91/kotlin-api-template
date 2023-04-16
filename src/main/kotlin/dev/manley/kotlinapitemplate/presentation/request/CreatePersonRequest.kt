package dev.manley.kotlinapitemplate.presentation.request

data class CreatePersonRequest(
    val name: String,
    val email: String,
    val password: String
)
