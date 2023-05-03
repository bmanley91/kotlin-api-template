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

class RetrievePersonByEmailUsecaseTest {

    private val testPerson = Person(
        id = UUID.randomUUID(),
        name = "John Name",
        email = "foo@bar.com",
        password = ""
    )

    @Test
    fun `retrieve person by email`() {
        val mockPersonRepository = mockk<PersonRepository>()
        val emailSlot = slot<String>()
        every { mockPersonRepository.findByEmail(email = capture(emailSlot)) } answers {
            testPerson.copy(email = emailSlot.captured)
        }

        val usecase = RetrievePersonByEmailUsecase(
            personRepository = mockPersonRepository
        )

        val result = usecase.execute(testPerson.email)

        verify(exactly = 1) { mockPersonRepository.findByEmail(email = testPerson.email) }
        assertEquals(testPerson, result)
    }
}
