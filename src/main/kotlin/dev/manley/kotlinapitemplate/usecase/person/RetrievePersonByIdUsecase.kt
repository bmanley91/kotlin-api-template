package dev.manley.kotlinapitemplate.usecase.person

import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.domain.repository.PersonRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Component
class RetrievePersonByIdUsecase(
    private val personRepository: PersonRepository
) {
    fun execute(id: UUID): Person? {
        logger.info { "Retrieving person by id: $id" }
        return personRepository.findById(id)
    }
}
