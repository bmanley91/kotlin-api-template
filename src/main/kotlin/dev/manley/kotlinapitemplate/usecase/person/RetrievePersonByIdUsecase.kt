package dev.manley.kotlinapitemplate.usecase.person

import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.domain.repository.PersonRepository
import mu.KLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RetrievePersonByIdUsecase(
    private val personRepository: PersonRepository
) {
    companion object : KLogging()
    fun execute(id: UUID): Person? {
        logger.info { "Retrieving person by id: $id" }
        return personRepository.findById(id)
    }
}