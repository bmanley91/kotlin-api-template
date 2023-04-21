package dev.manley.kotlinapitemplate.presentation.controller

import dev.manley.kotlinapitemplate.presentation.response.HealthResponse
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
