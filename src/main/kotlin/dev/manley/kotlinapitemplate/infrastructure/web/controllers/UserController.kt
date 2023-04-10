package dev.manley.kotlinapitemplate.infrastructure.web.controllers

import dev.manley.kotlinapitemplate.application.dtos.CreateUserResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController {
    @PostMapping
    fun createUser(): CreateUserResponse {
        return CreateUserResponse(id = UUID.randomUUID())
    }
}