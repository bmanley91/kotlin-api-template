package dev.manley.kotlinapitemplate.infrastructure.web.controllers

import dev.manley.kotlinapitemplate.application.dtos.HealthResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthController {

    @GetMapping
    fun healthCheck(): HealthResponse =
        HealthResponse()

}
