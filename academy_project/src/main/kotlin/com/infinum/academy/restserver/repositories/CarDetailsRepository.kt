package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarDetails
import org.springframework.data.repository.Repository

interface CarDetailsRepository : Repository<CarDetails, Long> {
    fun save(carDetails: CarDetails): CarDetails
    fun saveAll(carDetails: Iterable<CarDetails>)
    fun findAll(): List<CarDetails>
    fun findById(id: Long): CarDetails
    fun findByManufacturerNameAndModelName(manufacturerName: String, modelName: String): CarDetails?
}
