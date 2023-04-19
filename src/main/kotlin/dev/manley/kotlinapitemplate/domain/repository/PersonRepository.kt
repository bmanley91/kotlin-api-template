package dev.manley.kotlinapitemplate.domain.repository

import dev.manley.kotlinapitemplate.domain.model.Person


interface PersonRepository {
    fun save(person: Person) : Person
    fun findByEmail(email: String) : Person?
}