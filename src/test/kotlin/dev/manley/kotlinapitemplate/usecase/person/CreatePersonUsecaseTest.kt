package dev.manley.kotlinapitemplate.usecase.person

import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.domain.repository.PersonRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class CreatePersonUsecaseTest {

    private val testPerson = Person(
        id = UUID.randomUUID(),
        name = "John Name",
        email = "john@name.biz",
        password = "abc123"
    )

    @Test
    fun `create person`() {
        val mockPersonRepository = mockk<PersonRepository>()
        val personSlot = slot<Person>()
        every { mockPersonRepository.save(person = capture(personSlot)) } answers {
            personSlot.captured
        }
        every { mockPersonRepository.findByEmail(email = any()) } returns null

        val usecase = CreatePersonUsecase(
            personRepository = mockPersonRepository
        )

        val result = usecase.execute(testPerson)

        verify { mockPersonRepository.save(person = testPerson) }
        assertEquals(testPerson, result)
    }

    @Test
    fun `create person with email in use`() {
        val mockPersonRepository = mockk<PersonRepository>()
        every { mockPersonRepository.findByEmail(email = testPerson.email) } returns testPerson
        val usecase = CreatePersonUsecase(
            personRepository = mockPersonRepository
        )

        var thrownException: Exception? = null
        try {
            usecase.execute(testPerson)
        } catch (e: Exception) {
            thrownException = e
        }

        verify { mockPersonRepository.findByEmail(email = testPerson.email) }
        assertEquals("A user with the email address '${testPerson.email}' already exists.", thrownException?.message)
    }
}
