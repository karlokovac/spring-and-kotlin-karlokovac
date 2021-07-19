package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.services.Service
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.net.URI

@Controller
class MainController(
    private val carService: Service,
) {

    @PostMapping("/cars")
    fun addCar(@RequestBody carDTO: CarDTO): ResponseEntity<Long> {
        val id = carService.addCar(carDTO)
        return ResponseEntity.created(URI.create("/cars/$id")).body(id)
    }

    @GetMapping("/cars/{carId}")
    fun fetchCar(@PathVariable carId: Long): ResponseEntity<Car> {
        return ResponseEntity.ok(carService.getCar(carId))
    }

    @PostMapping("/carCheckUps")
    fun addCarCheckUp(@RequestBody carCheckUpDTO: CarCheckUpDTO): ResponseEntity<Long> {
        val id = carService.addCheckUp(carCheckUpDTO)
        return ResponseEntity.ok(id)
    }
}
