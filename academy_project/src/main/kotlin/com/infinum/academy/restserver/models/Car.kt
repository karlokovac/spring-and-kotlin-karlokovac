package com.infinum.academy.restserver.models

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "CAR")
data class Car(
    @Column(name = "ownerid")
    val ownerId: Long,
    @Column(name = "dateadded")
    val dateAdded: LocalDate,
    @Column(name = "manufacturername")
    val manufacturerName: String,
    @Column(name = "modelname")
    val modelName: String,
    @Column(name = "productionyear")
    val productionYear: Int,
    @Column(name = "serialnumber")
    val serialNumber: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAR_SEQ")
    @SequenceGenerator(name = "CAR_SEQ", sequenceName = "CAR_SEQ", allocationSize = 1)
    val id: Long = 0,

    @Transient
    val carCheckUps: List<CarCheckUp> = emptyList()
)

data class AddCarDTO(
    val ownerId: Long,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Int,
    val serialNumber: Long,
)

fun AddCarDTO.toDomainModel() = Car(ownerId, LocalDate.now(), manufacturerName, modelName, productionYear, serialNumber)
