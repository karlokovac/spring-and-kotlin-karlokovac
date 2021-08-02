package com.infinum.academy.restserver.models

import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "CAR")
data class CarEntity(
    val ownerId: Long,
    val dateAdded: LocalDate,
    @ManyToOne
    val carDetails: CarDetailsEntity,
    val productionYear: Short,
    val serialNumber: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAR_SEQ")
    @SequenceGenerator(name = "CAR_SEQ", sequenceName = "CAR_SEQ", allocationSize = 1)
    val id: Long = 0,
)

fun CarEntity.toResourceModel() = CarResource(ownerId, dateAdded, carDetails, productionYear, serialNumber, id)

data class AddCarDTO(
    val ownerId: Long,
    val manufacturerName: String,
    val modelName: String,
    val productionYear: Short,
    val serialNumber: Long,
)

fun AddCarDTO.toEntityModel(carDetailsEntity: CarDetailsEntity) =
    CarEntity(ownerId, LocalDate.now(), carDetailsEntity, productionYear, serialNumber)

@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CarResource(
    val ownerId: Long,
    val dateAdded: LocalDate,
    val carDetails: CarDetailsEntity,
    val productionYear: Short,
    val serialNumber: Long,
    val id: Long = 0,
) : RepresentationModel<CarResource>()
