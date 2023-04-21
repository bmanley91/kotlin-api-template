package dev.manley.kotlinapitemplate.domain.exception

import dev.manley.kotlinapitemplate.domain.model.Person

class PersonCreationException(person: Person) :
    RuntimeException("Failed to create person: $person")
