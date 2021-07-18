package com.infinum.academy.restserver

import com.fasterxml.jackson.databind.ObjectMapper
import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.services.Service
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest
class MainControllerTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper
) {
    @MockkBean
    lateinit var carService: Service

    @BeforeEach
    fun setUp(){
        Car.counter = 1
        CarCheckUp.counter = 1
    }

    @Test
    fun testAddingCar(){
        val carDTO = CarDTO(1L,"Ford","Ka", 2010,12345L)
        val car = Car(carDTO)

        every{
            carService.addCar(any())
        } returns car

        mvc.post("/addCar"){
            content = mapper.writeValueAsString(carDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.id") { value("1") }
        }
    }

    @Test
    fun testAddingCarCarCheckUp(){
        val carCheckUpDTO = CarCheckUpDTO("Ante Antic",145f,12345L)
        val carCheckUp = CarCheckUp(carCheckUpDTO)

        every{
            carService.addCheckUp(any())
        } returns carCheckUp

        mvc.post("/addCarCheckUp"){
            content = mapper.writeValueAsString(carCheckUpDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.id") { value("1") }
        }
    }

    @Test
    fun testfetchingExistingCar(){
        val car = Car(1L,"Ford","Ka", 2010,12345L,1)

        every{
            carService.getCar(any())
        } returns car

        mvc.get("/fetchCar?id=1").andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.id") { value("1") }
            jsonPath("$.manufacturerName") {value("Ford")}
        }
    }

    @Test
    fun testFetchingNonExistingCar(){
        every{
            carService.getCar(any())
        } returns null

        mvc.get("/fetchCar?id=1").andExpect {
            status { is4xxClientError() }
        }
    }
}