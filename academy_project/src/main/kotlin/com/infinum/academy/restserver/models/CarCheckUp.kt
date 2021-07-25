package com.infinum.academy.restserver.models

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "CHECKUP")
data class CarCheckUp(
    @Column(name = "workername")
    val workerName: String,
    val price: Double,
    @Column(name = "carid")
    val carId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHECKUP_SEQ")
    @SequenceGenerator(name = "CHECKUP_SEQ", sequenceName = "CHECKUP_SEQ", allocationSize = 1)
    val id: Long = 0,
    @Column(name = "datetime")
    val dateTime: LocalDateTime = LocalDateTime.now()
)

data class AddCarCheckUpDTO(
    val workerName: String,
    val price: Double,
    val carId: Long
)

fun AddCarCheckUpDTO.toDomainModel() = CarCheckUp(workerName, price, carId)
