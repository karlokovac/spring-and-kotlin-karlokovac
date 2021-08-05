package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarDetailsEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface CarDetailsRepository : Repository<CarDetailsEntity, Long> {
    fun save(carDetailsEntity: CarDetailsEntity): CarDetailsEntity
    fun saveAll(carDetailsEntity: Iterable<CarDetailsEntity>)
    fun findAll(): List<CarDetailsEntity>
    fun findById(id: Long): CarDetailsEntity
    fun findByManufacturerNameAndModelName(manufacturerName: String, modelName: String): CarDetailsEntity?

    @Query("SELECT DISTINCT cd FROM CarDetailsEntity cd WHERE cd.id IN (SELECT c.carDetails FROM CarEntity c)")
    fun findAllStoredModels(): List<CarDetailsEntity>
}
