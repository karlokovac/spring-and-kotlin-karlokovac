package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.repositories.Repository
import org.springframework.stereotype.Component

@Component
class Service(
    val carRepository: Repository
){

    fun addCar(carDTO: CarDTO): Car?{
        val car = Car(carDTO)
        if( carRepository.addCar(car) )
            return car
        return null
    }

    fun getCar(id: Long): Car?{
        return carRepository.getCar(id)
    }

    fun addCheckUp(carCheckUpDTO: CarCheckUpDTO): CarCheckUp?{
        val carCheckUp = CarCheckUp(carCheckUpDTO)
        if( carRepository.addCarCheckUp(carCheckUp) )
            return carCheckUp
        return null
    }

}