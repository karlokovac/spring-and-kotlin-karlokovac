package com.infinum.academy.restserver.services

import com.fasterxml.jackson.annotation.JsonProperty
import com.infinum.academy.restserver.models.CarDetailsDTO
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class HttpCarDataService(
    private val webClient: WebClient
) {
    fun getAllCarData(): List<CarDetailsDTO>? {
        return webClient
            .get()
            .uri("/api/v1/cars")
            .retrieve()
            .bodyToMono<AllCarDetailsResponse>()
            .map { response -> response.data }
            .block()
    }
}

data class AllCarDetailsResponse(
    @JsonProperty("data") val data: List<CarDetailsDTO>
)
