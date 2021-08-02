package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.models.toCarDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import com.infinum.academy.restserver.repositories.CarRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CarService(
    val carRepository: CarRepository,
    val carCheckUpRepository: CarCheckUpRepository,
    val carDetailsValidationService: CarDetailsValidationService
) {

    fun addCar(carDTO: AddCarDTO): Long {
        val details = carDetailsValidationService.getDetailsId(
            carDTO.manufacturerName,
            carDTO.modelName
        )
        return carRepository.save(carDTO.toDomainModel(details)).id
    }

    fun getCar(id: Long): CarDTO {
        val car = carRepository.findById(id)
        return car.toCarDTO(
            carCheckUpRepository.findByCarIdOrderByDateTimeDesc(id)
        )
    }

    fun getAllCars(pageable: Pageable) =
        carRepository.findAll(pageable).map {
            it.toCarDTO()
        }
}
