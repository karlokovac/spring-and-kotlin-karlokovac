package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.AddCarCheckUpDTO
import com.infinum.academy.restserver.models.toCarWithCheckUps
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CarCheckUpService(
    val carCheckUpRepository: CarCheckUpRepository
) {

    fun addCheckUp(carCheckUpDTO: AddCarCheckUpDTO): Long {
        return try {
            carCheckUpRepository.save(carCheckUpDTO.toCarWithCheckUps()).id
        } catch (ex: DataIntegrityViolationException) {
            println(ex)
            throw ResponseStatusException(HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getAllCheckUpsForCarId(carId: Long, pageable: Pageable) =
        carCheckUpRepository.findByCarId(carId, pageable)
}
