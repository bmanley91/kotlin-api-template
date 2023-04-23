package dev.manley.kotlinapitemplate.presentation.controller

import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.presentation.request.CreatePersonRequest
import dev.manley.kotlinapitemplate.usecase.person.CreatePersonUsecase
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class PersonControllerTest {

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
            name = "John Name",
            email = "john@name.biz",
            password = "abc123"
        )
        val response = controller.createPerson(request)

        verify(exactly = 1) { mockCreatePersonUsecase.execute(person = any()) }
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(personSlot.captured.name, response.body?.name)
        assertEquals(personSlot.captured.email, response.body?.email)
        assertEquals(personSlot.captured.id, response.body?.id)
    }
}
