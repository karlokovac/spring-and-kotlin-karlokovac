package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.StoredCarDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface CarRepository : Repository<StoredCarDTO, Long> {
    fun save(car: StoredCarDTO): StoredCarDTO
    fun findById(id: Long): StoredCarDTO
    fun findAll(pageable: Pageable): Page<StoredCarDTO>
}
