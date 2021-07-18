package com.infinum.academy.restserver

import com.fasterxml.jackson.databind.ObjectMapper
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class RestServerApplicationTests @Autowired constructor(
    private val mapper: ObjectMapper
) {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun fetchNonExistingCar() {
        mvc.get("/fetchCar?id=10345432")
            .andExpect {
                status { is4xxClientError() }
            }
    }
    @Test
    fun testAddingCar() {
        val carDTO = CarDTO(1L, "Audi", "R8", 2015, 123456789L)
        mvc.post("/addCar") {
            content = mapper.writeValueAsString(carDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.manufacturerName") { value("Audi") }
        }
    }

    @Test
    fun fetchExistingCar() {
        val carDTO = CarDTO(1L, "Audi", "R8", 2015, 123456789L)
        mvc.post("/addCar") {
            content = mapper.writeValueAsString(carDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.manufacturerName") { value("Audi") }
        }
        mvc.get("/fetchCar?id=1")
            .andExpect {
                status { is2xxSuccessful() }
                jsonPath("$.manufacturerName") { value("Audi") }
            }
    }

    @Test
    fun testAddingCarCheckUp() {
        val carDTO = CarDTO(1L, "Audi", "R8", 2015, 123456789L)
        mvc.post("/addCar") {
            content = mapper.writeValueAsString(carDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.manufacturerName") { value("Audi") }
        }
        val carCheckUpDTO = CarCheckUpDTO("Iva Ivić", 200f, 1)
        mvc.post("/addCarCheckUp") {
            content = mapper.writeValueAsString(carCheckUpDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.workerName") { value("Iva Ivić") }
        }
    }

    @Test
    fun testAddingCarCheckUpToNonExistingCar() {
        val carCheckUpDTO = CarCheckUpDTO("Iva Ivić", 200f, 164689L)
        mvc.post("/addCarCheckUp") {
            content = mapper.writeValueAsString(carCheckUpDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is4xxClientError() }
        }
    }
}
