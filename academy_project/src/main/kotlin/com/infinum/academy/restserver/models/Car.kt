package com.infinum.academy.restserver.models

import java.time.LocalDate

data class Car constructor(
    val ownerId: Long,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Int,
    val serialNumber: Long,

    val id: Long = generateUniqueCode(),
    val dateAdded: LocalDate = LocalDate.now(),
    val carCheckUps: MutableList<CarCheckUp> = mutableListOf(),
){
    constructor(carDTO: CarDTO) : this(
        carDTO.ownerId,
        carDTO.manufacturerName,
        carDTO.modelName,
        carDTO.productionYear,
        carDTO.serialNumber
    )

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