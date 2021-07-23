package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import org.springframework.stereotype.Service

@Service
class CarCheckUpService(
    val carCheckUpRepository: CarCheckUpRepository
) {

    fun addCheckUp(carCheckUpDTO: CarCheckUpDTO): Long {
        return carCheckUpRepository.save(carCheckUpDTO.toDomainModel())
    }
}
