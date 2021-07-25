package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarCheckUp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface CarCheckUpRepository : Repository<CarCheckUp, Long> {
    fun save(carCheckUp: CarCheckUp): CarCheckUp
    fun findByCarIdOrderByDateTimeDesc(carId: Long): List<CarCheckUp>
    fun findByCarId(carId: Long, pageable: Pageable): Page<CarCheckUp>
}
