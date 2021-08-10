package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface CarRepository : Repository<CarEntity, Long> {
    fun save(carEntity: CarEntity): CarEntity
    fun findById(id: Long): CarEntity
    fun findAll(pageable: Pageable): Page<CarEntity>
    fun deleteById(id: Long)
}
