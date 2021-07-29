package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.CarDetails
import com.infinum.academy.restserver.repositories.CarDetailsRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CarDetailsValidationService(
    val carDetailsRepository: CarDetailsRepository
) {
    @Cacheable("names")
    fun getDetailsId(manufacturerName: String, modelName: String): CarDetails {
        return carDetailsRepository.findByManufacturerNameAndModelName(manufacturerName, modelName)
    }
}
