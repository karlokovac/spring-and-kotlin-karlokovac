package com.infinum.academy.restserver

import com.fasterxml.jackson.databind.ObjectMapper
import com.infinum.academy.restserver.assemblers.CarResourceAssembler
import com.infinum.academy.restserver.assemblers.CheckUpResourceAssembler
import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpEntity
import com.infinum.academy.restserver.models.CarDetailsEntity
import com.infinum.academy.restserver.models.CarEntity
import com.infinum.academy.restserver.models.CarResource
import com.infinum.academy.restserver.services.CarCheckUpService
import com.infinum.academy.restserver.services.CarDetailsService
import com.infinum.academy.restserver.services.CarService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Page
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.LocalDateTime

@WebMvcTest
class MainControllerTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper
) {
    @MockkBean
    lateinit var carService: CarService
    @MockkBean
    lateinit var carCheckUpService: CarCheckUpService
    @MockkBean
    lateinit var carDetailsService: CarDetailsService
    @MockkBean
    lateinit var carResourceAssembler: CarResourceAssembler
    @MockkBean
    lateinit var carPagedResourceAssembler: PagedResourcesAssembler<CarEntity>
    @MockkBean
    lateinit var checkUpResourceAssembler: CheckUpResourceAssembler
    @MockkBean
    lateinit var checkUpPagedResourceAssembler: PagedResourcesAssembler<CarCheckUpEntity>

    @Test
    @WithMockUser(authorities = ["SCOPE_ADMIN"])
    fun testAddingCar() {
        val carDTO = AddCarDTO(1L, "Ford", "Ka", 2010, 12345L)

        every {
            carService.addCar(carDTO)
        } returns 1L

        mvc.post("/cars") {
            content = mapper.writeValueAsString(carDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_ADMIN"])
    fun testAddingCarCarCheckUp() {
        val carCheckUp = CarCheckUp("Ante Antic", 145.0, 12345L, LocalDateTime.now())

        every {
            carCheckUpService.addCheckUp(carCheckUp)
        } returns 1L

        mvc.post("/checkups") {
            content = mapper.writeValueAsString(carCheckUp)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_ADMIN"])
    fun testFetchingExistingCar() {
        val car = CarEntity(1L, LocalDate.EPOCH, CarDetailsEntity("Ford", "Ka", true), 2010, 12345L, 1L)

        every {
            carService.getCar(1L)
        } returns car
        every {
            carResourceAssembler.toModel(car)
        } returns CarResource(1L, LocalDate.EPOCH, CarDetailsEntity("Ford", "Ka", true), 2010, 12345L, 1L)
        mvc.get("/cars/1").andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.id") { value("1") }
        }
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_ADMIN"])
    fun testFetchingNonExistingCar() {
        every {
            carService.getCar(any())
        } throws ResponseStatusException(HttpStatus.NOT_FOUND)

        mvc.get("/cars/1").andExpect {
            status { is4xxClientError() }
        }
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_ADMIN"])
    fun testFetchingAllCarsPage() {
        val page = Page.empty<CarEntity>()
        every {
            carService.getAllCars(any())
        } returns page

        mvc.get("/cars").andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.page.size") { value("0") }
        }
    }
}
