package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.models.toStoredCarDTO
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import com.infinum.academy.restserver.repositories.CarRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CarService(
    val carRepository: CarRepository,
    val carCheckUpRepository: CarCheckUpRepository
) {

    fun addCar(carDTO: AddCarDTO): Long {
        return carRepository.save(carDTO.toStoredCarDTO()).id
    }

    fun getCar(id: Long) =
        carRepository.findById(id).toDomainModel(
            carCheckUpRepository.findByCarIdOrderByDateTimeDesc(id)
        )

    fun getAllCars(pageable: Pageable) =
        carRepository.findAll(pageable)
}
