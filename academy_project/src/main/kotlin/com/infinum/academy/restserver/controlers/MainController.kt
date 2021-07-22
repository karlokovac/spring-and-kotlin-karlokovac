package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.models.CarWithCheckUps
import com.infinum.academy.restserver.services.Service
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
    fun fetchCar(@PathVariable carId: Long): ResponseEntity<CarWithCheckUps> {
        return ResponseEntity.ok(carService.getCarWithCheckUps(carId))
    }

    @PostMapping("/carCheckUps")
    fun addCarCheckUp(@RequestBody carCheckUpDTO: CarCheckUpDTO): ResponseEntity<Long> {
        val id = carService.addCheckUp(carCheckUpDTO)
        return ResponseEntity.ok(id)
    }
}
