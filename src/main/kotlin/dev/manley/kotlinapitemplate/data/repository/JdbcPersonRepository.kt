package dev.manley.kotlinapitemplate.data.repository

import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.domain.repository.PersonRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JdbcPersonRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : PersonRepository {
    private val userRowMapper = RowMapper { rs, _ ->
        Person(
            id = rs.getObject("id") as UUID,
            name = rs.getString("name"),
            email = rs.getString("email"),
            password = rs.getString("hashed_password")
        )
    }

    override fun save(person: Person): Person {
        val sql = "INSERT INTO person (name, email, hashed_password) VALUES (:name, :email, :password);"
        val params = MapSqlParameterSource()
            .addValue("name", person.name)
            .addValue("email", person.email)
            .addValue("password", person.password)

        jdbcTemplate.update(sql, params)

        val createdUser = findByEmail(person.email)
        return createdUser ?: throw RuntimeException("User creation failed")
    }

    override fun findByEmail(email: String): Person? {
        val sql = "SELECT id, name, email, hashed_password FROM person WHERE email = :email;"
        val params = MapSqlParameterSource().addValue("email", email)

        return jdbcTemplate.query(sql, params, userRowMapper).firstOrNull()
    }

    override fun findById(id: UUID): Person? {
        val sql = "SELECT id, name, email, hashed_password FROM person WHERE id = :id;"
        val params = MapSqlParameterSource().addValue("id", id)

        return jdbcTemplate.query(sql, params, userRowMapper).firstOrNull()
    }
}