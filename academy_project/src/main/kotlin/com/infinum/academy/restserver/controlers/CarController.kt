package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.services.CarService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URI

@Controller
@RequestMapping("/cars")
class CarController(
    private val carService: CarService
) {

    @PostMapping()
    fun addCar(@RequestBody carDTO: CarDTO): ResponseEntity<Unit> {
        val id = carService.addCar(carDTO)
        return ResponseEntity.created(URI.create("/cars/$id")).build()
    }

    @GetMapping("/{carId}")
    fun fetchCar(@PathVariable carId: Long): ResponseEntity<Car> {
        return ResponseEntity.ok(carService.getCarWithCheckUps(carId))
    }
}
