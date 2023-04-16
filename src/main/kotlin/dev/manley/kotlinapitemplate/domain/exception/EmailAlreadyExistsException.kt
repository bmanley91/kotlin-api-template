package dev.manley.kotlinapitemplate.domain.exception

class EmailAlreadyExistsException(email: String) :
    RuntimeException("A user with the email address '$email' already exists.")