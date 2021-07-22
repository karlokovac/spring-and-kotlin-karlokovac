package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.services.CarCheckUpService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URI

@Controller
@RequestMapping("/carCheckUps")
class CarCheckUpController(
    private val carCheckUpService: CarCheckUpService
) {
    @PostMapping("")
    fun addCarCheckUp(@RequestBody carCheckUpDTO: CarCheckUpDTO): ResponseEntity<Unit> {
        val id = carCheckUpService.addCheckUp(carCheckUpDTO)
        return ResponseEntity.created(URI.create("/cars/$id")).build()
    }
}
