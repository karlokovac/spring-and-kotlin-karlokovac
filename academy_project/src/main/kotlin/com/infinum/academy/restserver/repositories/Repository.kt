package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUp
import org.springframework.stereotype.Component

@Component
class Repository {
    private val cars = mutableMapOf<Long, Car>()
    private val carCheckUps = mutableMapOf<Long, CarCheckUp>()

    fun addCar(car: Car): Boolean{
        cars.putIfAbsent(car.id,car)?.also { return false }
        println("$car added.")
        return true
    }

    fun getCar(id: Long): Car?{
        return cars[id]
    }

    fun addCarCheckUp(carCheckUp: CarCheckUp): Boolean {
        if( cars[carCheckUp.carId]==null || carCheckUps[carCheckUp.id]!=null)
            return false
        carCheckUps[carCheckUp.id] = carCheckUp
        cars[carCheckUp.carId]?.carCheckUps?.add(carCheckUp)
        println("$carCheckUp added.")
        return true
    }

}