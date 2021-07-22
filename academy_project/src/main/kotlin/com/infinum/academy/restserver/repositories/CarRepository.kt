package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.Car

interface CarRepository {
    fun save(car: Car): Long
    fun findById(id: Long): Car
}