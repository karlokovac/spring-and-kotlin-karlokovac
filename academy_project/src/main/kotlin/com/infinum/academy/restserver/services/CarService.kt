package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.CarEntity
import com.infinum.academy.restserver.models.toEntityModel
import com.infinum.academy.restserver.repositories.CarRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CarService(
    val carRepository: CarRepository,
    val carDetailsValidationService: CarDetailsService
) {

    fun addCar(carDTO: AddCarDTO): Long {
        val details = carDetailsValidationService.getDetailsId(
            carDTO.manufacturerName,
            carDTO.modelName
        )
        return carRepository.save(carDTO.toEntityModel(details)).id
    }

    fun getCar(id: Long): CarEntity {
        return carRepository.findById(id)
    }

    fun getAllCars(pageable: Pageable): Page<CarEntity> {
        return carRepository.findAll(pageable)
    }

    fun deleteCarWithId(id: Long) {
        return carRepository.deleteById(id)
    }
}
