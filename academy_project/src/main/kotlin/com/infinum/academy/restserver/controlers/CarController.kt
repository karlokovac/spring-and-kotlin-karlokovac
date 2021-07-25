package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.services.CarService
import org.springframework.data.domain.Pageable
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
    fun addCar(@RequestBody carDTO: AddCarDTO): ResponseEntity<Unit> {
        val id = carService.addCar(carDTO)
        return ResponseEntity.created(URI.create("http://localhost:8080/cars/$id")).build()
    }

    @GetMapping()
    fun getAllCars(pageable: Pageable) =
        ResponseEntity.ok(carService.getAllCars(pageable))

    @GetMapping("/{carId}")
    fun fetchCar(@PathVariable carId: Long) =
        ResponseEntity.ok(carService.getCar(carId))
}
