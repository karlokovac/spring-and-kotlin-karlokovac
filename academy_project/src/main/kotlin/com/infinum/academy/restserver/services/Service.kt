package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.*
import com.infinum.academy.restserver.repositories.CarNotInRepository
import com.infinum.academy.restserver.repositories.Repository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class Service(
    val carRepository: Repository<Long,Car>,
    val carCheckUpRepository: Repository<Long,CarCheckUp>
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
        var id: Long
        try {
            id = carCheckUpRepository.save(carCheckUpDTO.toDomainModel())
        }catch (exc: CarNotInRepository){
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        return id
    }
}
