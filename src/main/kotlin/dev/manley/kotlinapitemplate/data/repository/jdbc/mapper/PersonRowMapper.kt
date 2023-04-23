package dev.manley.kotlinapitemplate.data.repository.jdbc.mapper

import dev.manley.kotlinapitemplate.domain.model.Person
import org.springframework.jdbc.core.RowMapper
import java.util.UUID

val personRowMapper = RowMapper { rs, _ ->
    Person(
        id = rs.getObject("id") as UUID,
        name = rs.getString("name"),
        email = rs.getString("email"),
        password = rs.getString("hashed_password")
    )
}
