package dev.manley.kotlinapitemplate.presentation.controller

import dev.manley.kotlinapitemplate.domain.exception.EmailAlreadyExistsException
import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.presentation.request.CreatePersonRequest
import dev.manley.kotlinapitemplate.usecase.person.CreatePersonUsecase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/persons")
class PersonController(private val createPersonUsecase: CreatePersonUsecase) {
    @PostMapping
    fun createPerson(@RequestBody createPersonRequest: CreatePersonRequest): ResponseEntity<Person> {
        val person = Person(
            id = UUID.randomUUID(),
            name = createPersonRequest.name,
            email = createPersonRequest.email,
            password = createPersonRequest.password
        )
        val createdPerson = createPersonUsecase.execute(person)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson)
    }

    @GetMapping("/:id")
    fun getPerson(@RequestParam id: UUID): ResponseEntity<Person> {
        return ResponseEntity.ok().build()
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailAlreadyExistsException(exception: EmailAlreadyExistsException): ResponseEntity<Any> {
        val responseBody = mapOf("error" to exception.message)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody)
    }
}