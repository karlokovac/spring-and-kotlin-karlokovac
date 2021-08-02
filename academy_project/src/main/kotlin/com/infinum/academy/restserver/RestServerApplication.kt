package com.infinum.academy.restserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class RestServerApplication

fun main() {
    runApplication<RestServerApplication>()
}
