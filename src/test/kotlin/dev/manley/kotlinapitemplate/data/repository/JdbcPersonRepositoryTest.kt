package dev.manley.kotlinapitemplate.data.repository

import dev.manley.kotlinapitemplate.data.repository.jdbc.JdbcPersonRepository
import dev.manley.kotlinapitemplate.data.repository.jdbc.mapper.personRowMapper
import dev.manley.kotlinapitemplate.domain.exception.PersonCreationException
import dev.manley.kotlinapitemplate.domain.model.Person
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `test save person fails`() {
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
        } answers { listOf() }

        val repository = JdbcPersonRepository(jdbcTemplate)

        assertThrows<PersonCreationException> {
            repository.save(testPerson)
        }

        verify(exactly = 1) { jdbcTemplate.update(saveSqlSlot.captured, saveParamsSlot.captured) }
        verify(exactly = 1) { jdbcTemplate.query(querySqlSlot.captured, queryParamsSlot.captured, personRowMapper) }
    }

    @Test
    fun `test find by email`() {
        val jdbcTemplate = mockk<NamedParameterJdbcTemplate>()
        val sqlSlot = slot<String>()
        val paramsSlot = slot<SqlParameterSource>()
        every {
            jdbcTemplate.query(
                capture(sqlSlot),
                capture(paramsSlot),
                personRowMapper
            )
        } answers { listOf(testPerson) }

        val repository = JdbcPersonRepository(jdbcTemplate)

        val result = repository.findByEmail(testPerson.email)

        verify(exactly = 1) { jdbcTemplate.query(sqlSlot.captured, paramsSlot.captured, personRowMapper) }
        assertEquals(testPerson, result)
    }

    @Test
    fun `test find by id`() {
        val jdbcTemplate = mockk<NamedParameterJdbcTemplate>()
        val sqlSlot = slot<String>()
        val paramsSlot = slot<SqlParameterSource>()
        every {
            jdbcTemplate.query(
                capture(sqlSlot),
                capture(paramsSlot),
                personRowMapper
            )
        } answers { listOf(testPerson) }

        val repository = JdbcPersonRepository(jdbcTemplate)

        val result = repository.findById(testPerson.id)

        verify(exactly = 1) { jdbcTemplate.query(sqlSlot.captured, paramsSlot.captured, personRowMapper) }
        assertEquals(testPerson, result)
    }
}
