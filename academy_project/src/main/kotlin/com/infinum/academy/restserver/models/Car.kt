package com.infinum.academy.restserver.models

import java.time.LocalDate

data class Car (
    val ownerId: Long,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Int,
    val serialNumber: Long,

    val id: Long = generateUniqueCode(),
    val dateAdded: LocalDate = LocalDate.now(),
    val carCheckUps: MutableList<CarCheckUp> = mutableListOf(),
) {
    companion object {
        var counter: Long = 1
        fun generateUniqueCode(): Long = counter++
    }
}

data class CarDTO(
    val ownerId: Long,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Int,
    val serialNumber: Long,
)

fun CarDTO.toDomainModel() = Car( ownerId, manufacturerName, modelName, productionYear, serialNumber )