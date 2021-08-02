package com.infinum.academy.restserver.services

import com.infinum.academy.restserver.models.CarDetails

interface CarDataService {
    fun getAllCarData(): List<CarDetails>?
}