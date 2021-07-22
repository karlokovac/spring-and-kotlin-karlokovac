package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarCheckUp

interface CarCheckUpRepository {
    fun save(carCheckUp: CarCheckUp): Long
    fun findAllByCarId(id: Long): List<CarCheckUp>
}
