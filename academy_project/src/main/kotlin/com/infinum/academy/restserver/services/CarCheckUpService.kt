package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpEntity
import com.infinum.academy.restserver.models.toCarCheckUpEntity
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.Period

@Service
class CarCheckUpService(
    val carCheckUpRepository: CarCheckUpRepository
) {

    fun addCheckUp(carCheckUp: CarCheckUp): Long {
        return try {
            carCheckUpRepository.save(carCheckUp.toCarCheckUpEntity()).id
        } catch (ex: DataIntegrityViolationException) {
            println(ex)
            throw ResponseStatusException(HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getOne(id: Long): CarCheckUpEntity {
        return carCheckUpRepository.findById(id)
    }

    fun getAllCheckUpsForCarId(carId: Long, pageable: Pageable): Page<CarCheckUpEntity> {
        return carCheckUpRepository.findByCarId(carId, pageable)
    }

    fun getLastTen(): List<CarCheckUpEntity> {
        return carCheckUpRepository.findFirst10ByDateTimeBeforeOrderByDateTimeDesc(LocalDateTime.now())
    }

    fun getWithinDuration(period: Period): List<CarCheckUpEntity> {
        return carCheckUpRepository.findByDateTimeBetweenOrderByDateTime(
            LocalDateTime.now(),
            LocalDateTime.now().plus(period)
        )
    }
}
