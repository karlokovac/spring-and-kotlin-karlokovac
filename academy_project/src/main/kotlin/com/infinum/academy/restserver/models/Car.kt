package com.infinum.academy.restserver.models

import java.time.LocalDate

data class Car(
    val id: Long,
    val ownerId: Long,
    val dateAdded: LocalDate,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Int,
    val serialNumber: Long,
)

data class CarWithCheckUps(
    val id: Long,
    val ownerId: Long,
    val dateAdded: LocalDate,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Int,
    val serialNumber: Long,
    var carCheckUps: List<CarCheckUp>? = null
)



data class CarDTO(
    val ownerId: Long,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Int,
    val serialNumber: Long,
)

fun CarDTO.toDomainModel() = Car(0,ownerId, LocalDate.now(),manufacturerName, modelName, productionYear, serialNumber)

