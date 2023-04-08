package dev.manley.kotlinapitemplate.infrastructure.web.controllers

import dev.manley.kotlinapitemplate.application.dtos.HealthResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals


class HealthControllerTest {
    @Test
    fun testHealthCheck() {
        val controller = HealthController()

        val response = controller.healthCheck()

        assertEquals(HealthResponse(healthy = true), response)
    }

}