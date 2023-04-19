package dev.manley.kotlinapitemplate.usecase.person

import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.domain.repository.PersonRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RetrievePersonByIdUsecase(
    private val personRepository: PersonRepository
) {
    fun execute(id: UUID): Person? {
        return personRepository.findById(id)
    }
}