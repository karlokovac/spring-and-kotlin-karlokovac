package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.models.toCarDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import com.infinum.academy.restserver.repositories.CarDetailsRepository
import com.infinum.academy.restserver.repositories.CarRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CarService(
    val carRepository: CarRepository,
    val carCheckUpRepository: CarCheckUpRepository,
    val carDetailsRepository: CarDetailsRepository,
    val carDetailsValidationService: CarDetailsValidationService
) {

    fun addCar(carDTO: AddCarDTO): Long {
        val id = carDetailsValidationService.getDetailsId(
            carDTO.manufacturerName,
            carDTO.modelName
        )
        return carRepository.save(carDTO.toDomainModel(id)).id
    }

    fun getCar(id: Long): CarDTO {
        val car = carRepository.findById(id)
        val carDetails = carDetailsRepository.findById(car.carDetailsId)
        return car.toCarDTO(
            carDetails,
            carCheckUpRepository.findByCarIdOrderByDateTimeDesc(id)
        )
    }

    fun getAllCars(pageable: Pageable) =
        carRepository.findAll(pageable).map {
            it.toCarDTO(
                carDetailsRepository.findById(it.carDetailsId),
                carCheckUpRepository.findByCarIdOrderByDateTimeDesc(it.id)
            )
        }
}
