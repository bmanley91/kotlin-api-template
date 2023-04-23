package dev.manley.kotlinapitemplate.presentation.controller

import dev.manley.kotlinapitemplate.domain.exception.EmailAlreadyExistsException
import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.presentation.request.CreatePersonRequest
import dev.manley.kotlinapitemplate.usecase.person.CreatePersonUsecase
import dev.manley.kotlinapitemplate.usecase.person.RetrievePersonByIdUsecase
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

class PersonControllerTest {

    private val email = "john@name.biz"
    private val name = "John Name"
    private val password = "abc123"

    @Test
    fun testCreatePerson() {
        val mockCreatePersonUsecase = mockk<CreatePersonUsecase>()
        val personSlot = slot<Person>()
        every { mockCreatePersonUsecase.execute(person = capture(personSlot)) } answers { personSlot.captured }
        val controller = PersonController(
            createPersonUsecase = mockCreatePersonUsecase,
            retrievePersonByIdUsecase = mockk()
        )

        val request = CreatePersonRequest(
            name = name,
            email = email,
            password = password
        )
        val response = controller.createPerson(request)

        verify(exactly = 1) { mockCreatePersonUsecase.execute(person = any()) }
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(personSlot.captured.name, response.body?.name)
        assertEquals(personSlot.captured.email, response.body?.email)
        assertEquals(personSlot.captured.id, response.body?.id)
    }

    @Test
    fun testCreatePersonEmailInUse() {
        val mockCreatePersonUsecase = mockk<CreatePersonUsecase>()
        val personSlot = slot<Person>()
        every { mockCreatePersonUsecase.execute(person = capture(personSlot)) } answers {
            throw EmailAlreadyExistsException(personSlot.captured.email)
        }
        val controller = PersonController(
            createPersonUsecase = mockCreatePersonUsecase,
            retrievePersonByIdUsecase = mockk()
        )

        var thrownException: EmailAlreadyExistsException? = null
        val request = CreatePersonRequest(
            name = name,
            email = email,
            password = password
        )
        try {
            controller.createPerson(request)
        } catch (e: EmailAlreadyExistsException) {
            thrownException = e
        }

        verify(exactly = 1) { mockCreatePersonUsecase.execute(person = any()) }
        assertEquals("A user with the email address '$email' already exists.", thrownException?.message)
    }

    @Test
    fun testEmailInUseExceptionHandler() {
        val controller = PersonController(
            createPersonUsecase = mockk(),
            retrievePersonByIdUsecase = mockk()
        )

        val response = controller.handleEmailAlreadyExistsException(EmailAlreadyExistsException(email))

        assertEquals(HttpStatus.CONFLICT, response.statusCode)
    }

    @Test
    fun testRetrievePersonById() {
        val mockRetrievePersonByIdUsecase = mockk<RetrievePersonByIdUsecase>()
        val idSlot = slot<UUID>()
        every { mockRetrievePersonByIdUsecase.execute(id = capture(idSlot)) } answers { Person(
            id = idSlot.captured,
            name = name,
            email = email,
            password = password
        )}
        val controller = PersonController(
            createPersonUsecase = mockk(),
            retrievePersonByIdUsecase = mockRetrievePersonByIdUsecase
        )

        val inputId = UUID.randomUUID()
        val response = controller.getPerson(inputId)

        verify(exactly = 1) { mockRetrievePersonByIdUsecase.execute(id = inputId) }
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(inputId, idSlot.captured)
    }

    @Test
    fun testGetPersonByIdNotFound() {
        val mockRetrievePersonByIdUsecase = mockk<RetrievePersonByIdUsecase>()
        val idSlot = slot<UUID>()
        every { mockRetrievePersonByIdUsecase.execute(id = capture(idSlot)) } answers { null }
        val controller = PersonController(
            createPersonUsecase = mockk(),
            retrievePersonByIdUsecase = mockRetrievePersonByIdUsecase
        )

        val inputId = UUID.randomUUID()
        val response = controller.getPerson(inputId)

        verify(exactly = 1) { mockRetrievePersonByIdUsecase.execute(id = inputId) }
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals(inputId, idSlot.captured)
    }
}
