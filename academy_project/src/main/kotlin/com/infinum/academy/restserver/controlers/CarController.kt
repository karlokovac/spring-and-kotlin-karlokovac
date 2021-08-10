package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.assemblers.CarResourceAssembler
import com.infinum.academy.restserver.assemblers.CheckUpResourceAssembler
import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.CarCheckUpEntity
import com.infinum.academy.restserver.models.CarCheckUpResource
import com.infinum.academy.restserver.models.CarDetailsPair
import com.infinum.academy.restserver.models.CarEntity
import com.infinum.academy.restserver.models.CarResource
import com.infinum.academy.restserver.services.CarCheckUpService
import com.infinum.academy.restserver.services.CarDetailsService
import com.infinum.academy.restserver.services.CarService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Controller
@RequestMapping("/cars")
class CarController(
    private val carService: CarService,
    private val carCheckUpService: CarCheckUpService,
    private val carDetailsService: CarDetailsService,
    private val carResourceAssembler: CarResourceAssembler,
    private val carCheckUpResourceAssembler: CheckUpResourceAssembler
) {

    @PostMapping
    fun addCar(@RequestBody carDTO: AddCarDTO): ResponseEntity<Unit> {
        val id = carService.addCar(carDTO)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(
                mapOf(
                    "id" to id
                )
            )
            .toUri()
        return ResponseEntity.created(location).build()
    }

    @GetMapping
    fun getAllCars(
        pageable: Pageable,
        pagedResourceAssembler: PagedResourcesAssembler<CarEntity>
    ): ResponseEntity<PagedModel<CarResource>> {
        return ResponseEntity.ok(
            pagedResourceAssembler.toModel(carService.getAllCars(pageable), carResourceAssembler)
        )
    }

    @GetMapping("/{carId}")
    fun fetchCar(@PathVariable carId: Long): ResponseEntity<CarResource> {
        return ResponseEntity.ok(
            carResourceAssembler.toModel(carService.getCar(carId))
        )
    }

    @GetMapping("/{carId}/checkups")
    fun getCarCheckUpsForCarId(
        @PathVariable carId: Long,
        pageable: Pageable,
        pagedResourceAssembler: PagedResourcesAssembler<CarCheckUpEntity>
    ): ResponseEntity<PagedModel<CarCheckUpResource>> {
        return ResponseEntity.ok(
            pagedResourceAssembler.toModel(
                carCheckUpService.getAllCheckUpsForCarId(carId, pageable),
                carCheckUpResourceAssembler
            )
        )
    }

    @GetMapping("/saved-models")
    fun fetchAllStoredModels(): ResponseEntity<List<CarDetailsPair>> {
        return ResponseEntity.ok(carDetailsService.getAllStoredCarPairs())
    }

    @DeleteMapping("/{carId}")
    fun deleteCar(
        @PathVariable carId: Long,
    ): ResponseEntity<Long> {
        carService.deleteCarWithId(carId)
        return ResponseEntity.ok(carId)
    }
}
