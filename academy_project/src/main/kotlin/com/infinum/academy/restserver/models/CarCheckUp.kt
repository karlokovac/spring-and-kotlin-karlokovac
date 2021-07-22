package com.infinum.academy.restserver.models

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.Period

data class CarCheckUp(
    val id: Long,
    val workerName: String,
    val price: Double,
    val carId: Long,

    val dateTime: LocalDateTime = LocalDateTime.now()
)

data class CarCheckUpDTO(
    val workerName: String,
    val price: Double,
    val carId: Long
)

fun CarCheckUpDTO.toDomainModel() = CarCheckUp(0,workerName, price, carId)
