package com.infinum.academy.restserver.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "CAR_DETAILS")
data class CarDetailsEntity(
    val manufacturerName: String,
    val modelName: String,
    val isCommon: Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAR_DETAILS_SEQ")
    @SequenceGenerator(name = "CAR_DETAILS_SEQ", sequenceName = "CAR_DETAILS_SEQ", allocationSize = 1)
    val id: Long = 0,
) : Serializable

data class CarDetails(
    @JsonProperty("manufacturer") val manufacturerName: String,
    @JsonProperty("model_name") val modelName: String,
    @JsonProperty("is_common") val isCommon: Boolean
)

fun CarDetails.toEntityModel() = CarDetailsEntity(manufacturerName, modelName, isCommon)
