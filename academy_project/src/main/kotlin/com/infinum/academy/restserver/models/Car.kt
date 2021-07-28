package com.infinum.academy.restserver.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "CAR")
data class Car(
    val ownerId: Long,
    val dateAdded: LocalDate,
    val carDetailsId: Long,
    val productionYear: Short,
    val serialNumber: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAR_SEQ")
    @SequenceGenerator(name = "CAR_SEQ", sequenceName = "CAR_SEQ", allocationSize = 1)
    val id: Long = 0,
)

fun Car.toCarDTO(
    carDetails: CarDetails,
    list: List<CarCheckUp>
) = CarDTO(
    ownerId, dateAdded, carDetails.manufacturerName, carDetails.modelName, carDetails.isCommon,
    productionYear, serialNumber, id, list
)

data class CarDTO(
    val ownerId: Long,
    val dateAdded: LocalDate,
    val manufacturerName: String,
    val modelName: String,
    val isCommon: Boolean,
    val productionYear: Short,
    val serialNumber: Long,
    val id: Long = 0,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val carCheckUps: List<CarCheckUp> = emptyList()
)

data class AddCarDTO(
    val ownerId: Long,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Short,
    val serialNumber: Long,
)

fun AddCarDTO.toDomainModel(carDetailsId: Long) =
    Car(ownerId, LocalDate.now(), carDetailsId, productionYear, serialNumber)
