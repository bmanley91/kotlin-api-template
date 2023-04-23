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

class RetrievePersonByIdUsecaseTest {

    private val testPerson = Person(
        id = UUID.randomUUID(),
        name = "John Name",
        email = "",
        password = ""
    )

    @Test
    fun `retrieve person by id`() {
        val mockPersonRepository = mockk<PersonRepository>()
        val idSlot = slot<UUID>()
        every { mockPersonRepository.findById(id = capture(idSlot)) } answers {
            testPerson.copy(id = idSlot.captured)
        }

        val usecase = RetrievePersonByIdUsecase(
            personRepository = mockPersonRepository
        )

        val result = usecase.execute(testPerson.id)

        verify(exactly = 1) { mockPersonRepository.findById(id = testPerson.id) }
        assertEquals(testPerson, result)
    }
}
