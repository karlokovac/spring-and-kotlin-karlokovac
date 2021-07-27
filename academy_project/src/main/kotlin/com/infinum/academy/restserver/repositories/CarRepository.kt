package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.Car
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface CarRepository : Repository<Car, Long> {
    fun save(car: Car): Car
    fun findById(id: Long): Car
    fun findAll(pageable: Pageable): Page<Car>
}
