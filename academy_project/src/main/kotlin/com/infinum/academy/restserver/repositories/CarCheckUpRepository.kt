package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarCheckUpEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.time.LocalDateTime

interface CarCheckUpRepository : Repository<CarCheckUpEntity, Long> {
    fun save(carCheckUpEntity: CarCheckUpEntity): CarCheckUpEntity
    fun findById(id: Long): CarCheckUpEntity
    fun findByCarIdOrderByDateTimeDesc(carId: Long): List<CarCheckUpEntity>
    fun findByCarId(carId: Long, pageable: Pageable): Page<CarCheckUpEntity>
    fun findFirst10ByDateTimeBeforeOrderByDateTimeDesc(dateTime: LocalDateTime): List<CarCheckUpEntity>
    fun findByDateTimeBetweenOrderByDateTime(from: LocalDateTime, to: LocalDateTime): List<CarCheckUpEntity>
    fun deleteById(id: Long)
}
