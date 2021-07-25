package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.Repository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class Service(
    val carRepository: Repository<Long, Car>,
    val carCheckUpRepository: Repository<Long, CarCheckUp>
) {

    fun addCar(carDTO: CarDTO): Long {
        return carRepository.save(carDTO.toDomainModel())
    }

    fun getCar(id: Long): Car {
        return carRepository.findById(id)?.also {
            it.carCheckUps.sortBy { carCheckUp -> carCheckUp.date }
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun addCheckUp(carCheckUpDTO: CarCheckUpDTO): Long {
        return carCheckUpRepository.save(carCheckUpDTO.toDomainModel())
    }
}
