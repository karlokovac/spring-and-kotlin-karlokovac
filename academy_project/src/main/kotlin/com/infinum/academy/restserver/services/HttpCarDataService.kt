package com.infinum.academy.restserver.services

import com.fasterxml.jackson.annotation.JsonProperty
import com.infinum.academy.restserver.models.CarDetails
import com.infinum.academy.restserver.models.CarDetailsEntity
import com.infinum.academy.restserver.models.toEntityModel
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class HttpCarDataService(
    private val webClient: WebClient
) : CarDataService {
    override fun getAllCarData(): List<CarDetailsEntity>? {
        return webClient
            .get()
            .uri("/api/v1/cars")
            .retrieve()
            .bodyToMono<AllCarDetailsResponse>()
            .map { response ->
                response.data.map { carDetailsDTO -> carDetailsDTO.toEntityModel() }
            }
            .block()
    }
}

data class AllCarDetailsResponse(
    @JsonProperty("data") val data: List<CarDetails>
)
