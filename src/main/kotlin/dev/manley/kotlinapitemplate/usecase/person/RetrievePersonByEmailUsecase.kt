package dev.manley.kotlinapitemplate.usecase.person

import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.domain.repository.PersonRepository
import org.springframework.stereotype.Component

@Component
class RetrievePersonByEmailUsecase(
    private val personRepository: PersonRepository
) {
    fun execute(email: String): Person? {
        return personRepository.findByEmail(email)
    }
}
