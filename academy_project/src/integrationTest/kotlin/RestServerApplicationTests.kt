package com.infinum.academy.restserver

import com.fasterxml.jackson.core.io.NumberInput
import com.fasterxml.jackson.databind.ObjectMapper
import com.infinum.academy.restserver.models.AddCarCheckUpDTO
import com.infinum.academy.restserver.models.AddCarDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest(classes = [RestServerApplication::class])
@AutoConfigureMockMvc
class RestServerApplicationTests @Autowired constructor(
    private val mapper: ObjectMapper,
) {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun canAddCar() {
        val carDTO = AddCarDTO(1L, "Audi", "R8", 2015, 1)
        mvc.post("/cars") {
            content = mapper.writeValueAsString(carDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    fun fetchExistingCar() {
        val carDTO = AddCarDTO(1L, "Audi", "R8", 2015, 2)
        val receivedId = mvc.post("/cars") {
            content = mapper.writeValueAsString(carDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }.andReturn().response.getHeaderValue("Location").toString()
            .split("/")[4]
        mvc.get("/cars/${NumberInput.parseLong(receivedId)}")
            .andExpect {
                status { is2xxSuccessful() }
            }
    }

    @Test
    fun testAddingCarCheckUp() {
        val carDTO = AddCarDTO(1L, "Audi", "R8", 2015, 3)
        val receivedId = mvc.post("/cars") {
            content = mapper.writeValueAsString(carDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }.andReturn().response.getHeaderValue("Location").toString()
            .split("/")[4]
        val carCheckUpDTO = AddCarCheckUpDTO("Iva Ivić", 200.0, NumberInput.parseLong(receivedId))
        mvc.post("/carCheckUps") {
            content = mapper.writeValueAsString(carCheckUpDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    fun testAddingCarCheckUpToNonExistingCar() {
        val carCheckUpDTO = AddCarCheckUpDTO("Iva Ivić", 200.0, 164689L)
        mvc.post("/carCheckUps") {
            content = mapper.writeValueAsString(carCheckUpDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is4xxClientError() }
        }
    }

    @Test
    fun fetchAllCarsPage() {
        mvc.get("/cars").andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.numberOfElements") { value("3") }
        }
    }

    @Test
    fun fetchAllCarCheckUpsPage() {
        mvc.get("/cars/1/checkUps").andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.numberOfElements") { value("1") }
        }
    }
}
