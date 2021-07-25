package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import com.infinum.academy.restserver.repositories.CarRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CarService(
    val carRepository: CarRepository,
    val carCheckUpRepository: CarCheckUpRepository
) {

    fun addCar(carDTO: AddCarDTO): Long {
        return try {
            carRepository.save(carDTO.toDomainModel()).id
        } catch (ex: DataIntegrityViolationException) {
            println(ex)
            throw ResponseStatusException(HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getCar(id: Long): Car {
        return try {
            carRepository.findById(id).copy(
                carCheckUps = carCheckUpRepository.findByCarIdOrderByDateTimeDesc(id)
            )
        } catch (ex: EmptyResultDataAccessException) {
            println(ex)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    fun getAllCars(pageable: Pageable) =
        carRepository.findAll(pageable)
}
