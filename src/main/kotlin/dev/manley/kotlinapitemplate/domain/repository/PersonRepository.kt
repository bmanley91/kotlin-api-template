package dev.manley.kotlinapitemplate.domain.repository

import dev.manley.kotlinapitemplate.domain.model.Person
import java.util.UUID


interface PersonRepository {
    fun save(person: Person) : Person
    fun findByEmail(email: String) : Person?
    fun findById(id: UUID) : Person?
}