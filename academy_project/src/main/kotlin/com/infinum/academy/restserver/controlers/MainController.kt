package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.services.Service
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MainController(
    private val carService: Service,
) {

    @PostMapping("/addCar")
    fun addCar(@RequestBody carDTO: CarDTO): ResponseEntity<Car> {
        carService.addCar(carDTO)?.also { return ResponseEntity(it, HttpStatus.OK) }
        return ResponseEntity(null, HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/fetchCar")
    fun fetchCar(@RequestParam id: Long): ResponseEntity<Car> {
        carService.getCar(id)?.also { return ResponseEntity(it, HttpStatus.OK) }
        return ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping("/addCarCheckUp")
    fun addCarCheckUp(@RequestBody carCheckUpDTO: CarCheckUpDTO): ResponseEntity<CarCheckUp> {
        carService.addCheckUp(carCheckUpDTO)?.also { return ResponseEntity(it, HttpStatus.OK) }
        return ResponseEntity(null, HttpStatus.BAD_REQUEST)
    }
}
