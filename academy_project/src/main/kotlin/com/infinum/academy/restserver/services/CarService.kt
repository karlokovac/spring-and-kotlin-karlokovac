package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.DatabaseCarCheckUpRepository
import com.infinum.academy.restserver.repositories.DatabaseCarRepository
import org.springframework.stereotype.Component

@Component
class CarService(
    val carRepository: DatabaseCarRepository,
    val carCheckUpRepository: DatabaseCarCheckUpRepository
) {

    fun addCar(carDTO: CarDTO): Long {
        return carRepository.save(carDTO.toDomainModel())
    }

    fun getCarWithCheckUps(id: Long): Car {
        val car = carRepository.findById(id)
        return car.copy(carCheckUps = carCheckUpRepository.findAllByCarId(id))
    }
}
