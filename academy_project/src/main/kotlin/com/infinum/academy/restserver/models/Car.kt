package com.infinum.academy.restserver.models

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

data class Car(
    val ownerId: Long,
    val dateAdded: LocalDate,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Short,
    val serialNumber: Long,
    val id: Long = 0,
    val carCheckUps: List<CarCheckUp> = emptyList()
)

@Entity
@Table(name = "CAR")
data class StoredCarDTO(
    val ownerId: Long,
    val dateAdded: LocalDate,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Short,
    val serialNumber: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAR_SEQ")
    @SequenceGenerator(name = "CAR_SEQ", sequenceName = "CAR_SEQ", allocationSize = 1)
    val id: Long = 0,
)

fun StoredCarDTO.toDomainModel(list: List<CarCheckUp>) =
    Car(ownerId, dateAdded, manufacturerName, modelName, productionYear, serialNumber, id, list)

data class AddCarDTO(
    val ownerId: Long,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Short,
    val serialNumber: Long,
)

fun AddCarDTO.toDomainModel() =
    Car(ownerId, LocalDate.now(), manufacturerName, modelName, productionYear, serialNumber)

fun AddCarDTO.toStoredCarDTO() =
    StoredCarDTO(ownerId, LocalDate.now(), manufacturerName, modelName, productionYear, serialNumber)
