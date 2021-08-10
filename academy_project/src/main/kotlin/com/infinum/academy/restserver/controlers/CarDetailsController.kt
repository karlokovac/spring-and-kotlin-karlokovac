package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.models.CarDetailsPair
import com.infinum.academy.restserver.services.CarDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/stored")
class CarDetailsController(
    private val carDetailsService: CarDetailsService
) {

    @GetMapping()
    fun fetchAllStoredModels(): ResponseEntity<List<CarDetailsPair>> {
        return ResponseEntity.ok(carDetailsService.getAllStoredCarPairs())
    }
}
