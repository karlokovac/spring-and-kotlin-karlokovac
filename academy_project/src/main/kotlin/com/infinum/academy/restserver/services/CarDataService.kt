package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.CarDetailsEntity

interface CarDataService {
    fun getAllCarData(): List<CarDetailsEntity>?
}
