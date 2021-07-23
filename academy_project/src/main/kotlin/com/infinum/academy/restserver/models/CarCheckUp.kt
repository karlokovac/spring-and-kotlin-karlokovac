package com.infinum.academy.restserver.models

import java.time.LocalDateTime

data class CarCheckUp(
    val workerName: String,
    val price: Double,
    val carId: Long,
    val id: Long = 0,
    val dateTime: LocalDateTime = LocalDateTime.now()
)

data class CarCheckUpDTO(
    val workerName: String,
    val price: Double,
    val carId: Long
)

fun CarCheckUpDTO.toDomainModel() = CarCheckUp(workerName, price, carId)
