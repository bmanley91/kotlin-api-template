package dev.manley.kotlinapitemplate.presentation.controller

import dev.manley.kotlinapitemplate.presentation.response.HealthResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HealthControllerTest {
    @Test
    fun testHealthCheck() {
        val controller = HealthController()

        val response = controller.healthCheck()

        assertEquals(HealthResponse(healthy = true), response)
    }
}
