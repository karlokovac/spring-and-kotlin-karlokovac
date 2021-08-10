package com.infinum.academy.restserver

import com.fasterxml.jackson.core.io.NumberInput
import com.fasterxml.jackson.databind.ObjectMapper
import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.CarCheckUp
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime

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
        val carCheckUpDTO = CarCheckUp("Iva Ivić", 200.0, NumberInput.parseLong(receivedId), LocalDateTime.now())
        mvc.post("/checkups") {
            content = mapper.writeValueAsString(carCheckUpDTO)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    fun testAddingCarCheckUpToNonExistingCar() {
        val carCheckUpDTO = CarCheckUp("Iva Ivić", 200.0, 164689L, LocalDateTime.now())
        mvc.post("/checkups") {
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
            jsonPath("$.page.totalElements") { value("3") }
        }
    }

    @Test
    fun fetchAllCarCheckUpsPage() {
        mvc.get("/cars/1/checkups").andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.page.totalElements") { value("1") }
        }
    }

    @Test
    fun fetchRecentCarCheckUpsPage() {
        mvc.get("/checkups/recent").andExpect {
            status { is2xxSuccessful() }
            jsonPath("$._embedded.item[0].carId") { value("1") }
        }
    }

    @Test
    fun fetchOneCheckUp() {
        mvc.get("/checkups/1").andExpect {
            status { is2xxSuccessful() }
            jsonPath("$.id") { value("1") }
        }
    }
}
