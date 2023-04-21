package dev.manley.kotlinapitemplate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinApiTemplateApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<KotlinApiTemplateApplication>(*args)
}
