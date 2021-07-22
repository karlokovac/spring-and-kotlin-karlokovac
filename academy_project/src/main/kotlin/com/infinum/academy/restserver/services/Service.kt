package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.models.CarWithCheckUps
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.DatabaseCarCheckUpRepository
import com.infinum.academy.restserver.repositories.DatabaseCarRepository
import org.springframework.stereotype.Component

@Component
class Service(
    val carRepository: DatabaseCarRepository,
    val carCheckUpRepository: DatabaseCarCheckUpRepository
) {

    fun addCar(carDTO: CarDTO): Long {
        return carRepository.save(carDTO.toDomainModel())
    }

    fun getCarWithCheckUps(id: Long): CarWithCheckUps {
        return carRepository.findById(id).also {
            it.carCheckUps = carCheckUpRepository.findAllByCarId(id)
        }
    }

    fun addCheckUp(carCheckUpDTO: CarCheckUpDTO): Long {
        return carCheckUpRepository.save(carCheckUpDTO.toDomainModel())
    }
}
