package com.infinum.academy.courseService

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class DataSource(
    @Value("\${spring.datasource.dbname}") val dbName: String,
    @Value("\${spring.datasource.username}") private val username: String,
    @Value("\${spring.datasource.password}") private val password: String
)