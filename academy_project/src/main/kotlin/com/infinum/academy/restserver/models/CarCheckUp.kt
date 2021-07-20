package com.infinum.academy.restserver.models

import java.time.LocalDate

data class CarCheckUp(
    val workerName: String,
    val price: Float,
    val carId: Long,

    val id: Long = generateUniqueCode(),
    val date: LocalDate = LocalDate.now()
) {
    companion object {
        var counter: Long = 1
        fun generateUniqueCode(): Long = counter++
    }
}

data class CarCheckUpDTO(
    val workerName: String,
    val price: Float,
    val carId: Long
)

fun CarCheckUpDTO.toDomainModel() = CarCheckUp(workerName, price, carId)
