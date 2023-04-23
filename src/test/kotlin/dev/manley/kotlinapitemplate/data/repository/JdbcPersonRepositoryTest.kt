package dev.manley.kotlinapitemplate.data.repository

import dev.manley.kotlinapitemplate.data.repository.jdbc.JdbcPersonRepository
import dev.manley.kotlinapitemplate.data.repository.jdbc.mapper.personRowMapper
import dev.manley.kotlinapitemplate.domain.model.Person
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import java.util.UUID

class JdbcPersonRepositoryTest {
    private val testPerson = Person(
        id = UUID.randomUUID(),
        name = "John Name",
        email = "john@name.biz",
        password = "abc123"
    )

    @Test
    fun `test save person`() {
        val jdbcTemplate = mockk<NamedParameterJdbcTemplate>()
        val saveSqlSlot = slot<String>()
        val saveParamsSlot = slot<SqlParameterSource>()
        every {
            jdbcTemplate.update(
                capture(saveSqlSlot),
                capture(saveParamsSlot)
            )
        } returns 1

        val querySqlSlot = slot<String>()
        val queryParamsSlot = slot<SqlParameterSource>()
        every {
            jdbcTemplate.query(
                capture(querySqlSlot),
                capture(queryParamsSlot),
                personRowMapper
            )
        } answers { listOf(testPerson) }

        val repository = JdbcPersonRepository(jdbcTemplate)

        val result = repository.save(testPerson)

        verify(exactly = 1) { jdbcTemplate.update(saveSqlSlot.captured, saveParamsSlot.captured) }
        verify(exactly = 1) { jdbcTemplate.query(querySqlSlot.captured, queryParamsSlot.captured, personRowMapper) }
        assertEquals(testPerson, result)
    }
}
