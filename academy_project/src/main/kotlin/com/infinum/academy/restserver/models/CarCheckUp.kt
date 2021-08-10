package com.infinum.academy.restserver.models

import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "CHECKUP")
data class CarCheckUpEntity(
    val workerName: String,
    val price: Double,
    val carId: Long,
    val dateTime: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHECKUP_SEQ")
    @SequenceGenerator(name = "CHECKUP_SEQ", sequenceName = "CHECKUP_SEQ", allocationSize = 1)
    val id: Long = 0
)

fun CarCheckUpEntity.toCarCheckUp() = CarCheckUp(workerName, price, id, dateTime)
fun CarCheckUpEntity.toResourceModel() = CarCheckUpResource(workerName, price, carId, dateTime, id)

data class CarCheckUp(
    val workerName: String,
    val price: Double,
    val carId: Long,
    val dateTime: LocalDateTime
)

fun CarCheckUp.toCarCheckUpEntity() = CarCheckUpEntity(workerName, price, carId, dateTime)

@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CarCheckUpResource(
    val workerName: String,
    val price: Double,
    val carId: Long,
    val dateTime: LocalDateTime,
    val id: Long
) : RepresentationModel<CarResource>()
