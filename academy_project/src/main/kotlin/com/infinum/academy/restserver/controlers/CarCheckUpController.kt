package com.infinum.academy.restserver.controlers

import com.infinum.academy.restserver.assemblers.CheckUpResourceAssembler
import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpResource
import com.infinum.academy.restserver.services.CarCheckUpService
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.Period

@Controller
@RequestMapping("/checkups")
class CarCheckUpController(
    private val carCheckUpService: CarCheckUpService,
    private val carCheckUpResourceAssembler: CheckUpResourceAssembler
) {
    @PostMapping()
    fun addCarCheckUp(@RequestBody carCheckUp: CarCheckUp): ResponseEntity<Unit> {

        val id = carCheckUpService.addCheckUp(carCheckUp)
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

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<CarCheckUpResource> {
        return ResponseEntity.ok(
            carCheckUpResourceAssembler.toModel(carCheckUpService.getOne(id))
        )
    }

    @GetMapping("/recent")
    fun lastTen(): ResponseEntity<CollectionModel<CarCheckUpResource>> {
        return ResponseEntity.ok(
            carCheckUpResourceAssembler.toCollectionModel(carCheckUpService.getLastTen())
        )
    }

    @GetMapping("/upcoming/{duration}")
    fun upcoming(@PathVariable duration: String): ResponseEntity<CollectionModel<CarCheckUpResource>> {
        val period = when (duration) {
            "" -> Period.ofMonths(1)
            "week" -> Period.ofWeeks(1)
            "month" -> Period.ofMonths(1)
            "half" -> Period.ofMonths(6)
            else -> throw WrongFormatException()
        }
        return ResponseEntity.ok(
            carCheckUpResourceAssembler.toCollectionModel(carCheckUpService.getWithinDuration(period))
        )
    }
}

class WrongFormatException : RuntimeException()
