package com.infinum.academy.restserver.models

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "CHECKUP")
data class CarCheckUp(
    val workerName: String,
    val price: Double,
    val carId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHECKUP_SEQ")
    @SequenceGenerator(name = "CHECKUP_SEQ", sequenceName = "CHECKUP_SEQ", allocationSize = 1)
    val id: Long = 0,
    val dateTime: LocalDateTime = LocalDateTime.now()
)

fun CarCheckUp.toCarCheckUpDTO() = CarCheckUpDTO(workerName, price, id, dateTime)

data class AddCarCheckUpDTO(
    val workerName: String,
    val price: Double,
    val carId: Long
)

fun AddCarCheckUpDTO.toCarWithCheckUps() = CarCheckUp(workerName, price, carId)

data class CarCheckUpDTO(
    val workerName: String,
    val price: Double,
    val id: Long = 0,
    val dateTime: LocalDateTime
)
