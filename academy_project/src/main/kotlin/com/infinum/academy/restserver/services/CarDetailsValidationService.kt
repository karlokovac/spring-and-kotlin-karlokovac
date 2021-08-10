package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.CarDetailsEntity
import com.infinum.academy.restserver.repositories.CarDetailsRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CarDetailsValidationService(
    val carDetailsRepository: CarDetailsRepository
) {
    @Cacheable("names")
    fun getDetailsId(manufacturerName: String, modelName: String): CarDetailsEntity {
        return carDetailsRepository.findByManufacturerNameAndModelName(manufacturerName, modelName)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Car details don't match any stored in repository")
    }
}
