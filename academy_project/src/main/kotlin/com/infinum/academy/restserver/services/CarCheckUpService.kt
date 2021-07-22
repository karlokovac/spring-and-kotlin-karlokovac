package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.DatabaseCarCheckUpRepository
import org.springframework.stereotype.Component

@Component
class CarCheckUpService(
    val carCheckUpRepository: DatabaseCarCheckUpRepository
) {

    fun addCheckUp(carCheckUpDTO: CarCheckUpDTO): Long {
        return carCheckUpRepository.save(carCheckUpDTO.toDomainModel())
    }
}
