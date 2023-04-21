package dev.manley.kotlinapitemplate.presentation.controller

import dev.manley.kotlinapitemplate.domain.exception.EmailAlreadyExistsException
import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.presentation.request.CreatePersonRequest
import dev.manley.kotlinapitemplate.usecase.person.CreatePersonUsecase
import dev.manley.kotlinapitemplate.usecase.person.RetrievePersonByIdUsecase
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/persons")
class PersonController(
    private val createPersonUsecase: CreatePersonUsecase,
    private val retrievePersonByIdUsecase: RetrievePersonByIdUsecase
) {

    companion object : KLogging()

    @PostMapping
    fun createPerson(@RequestBody createPersonRequest: CreatePersonRequest): ResponseEntity<Person> =
        Person(
            id = UUID.randomUUID(),
            name = createPersonRequest.name,
            email = createPersonRequest.email,
            password = createPersonRequest.password
        )
            .let { person ->
                logger.info { "Creating person: $person" }
                createPersonUsecase.execute(person)
            }
            .let { createdPerson ->
                logger.info { "Created person: $createdPerson" }
                return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson)
            }

    @GetMapping("/{id}")
    fun getPerson(@PathVariable id: UUID): ResponseEntity<Person> =
        retrievePersonByIdUsecase.execute(id)
            .takeIf { it != null }
            ?.let {
                logger.info { "Retrieved person: $it" }
                return ResponseEntity.ok(it)
            }
            ?: run {
                logger.info { "Person with id $id not found." }
                return ResponseEntity.notFound().build()
            }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailAlreadyExistsException(exception: EmailAlreadyExistsException): ResponseEntity<Any> =
        mapOf("error" to exception.message)
            .let { return ResponseEntity.status(HttpStatus.CONFLICT).body(it) }
}
