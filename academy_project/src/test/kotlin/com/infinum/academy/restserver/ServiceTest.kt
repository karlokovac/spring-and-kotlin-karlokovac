package com.infinum.academy.restserver

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.repositories.InMemoryRepository
import com.infinum.academy.restserver.services.Service
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ServiceTest {
    private val repository = mockk<InMemoryRepository>()
    private lateinit var carService: Service

    @BeforeEach
    fun setUp() {
        carService = Service(repository)
        Car.counter = 1
        CarCheckUp.counter = 1
    }

    @Test
    fun testAddingCar() {
        val passedCarDTO1 = CarDTO(1L, "Ford", "Ka", 2010, 12345L)
        val passedCarDTO2 = CarDTO(1L, "Ford", "Ka", 2010, 34567L)

        every {
            repository.addCar(any())
        } returns true

        val actualCar1 = carService.addCar(passedCarDTO1)
        val actualCar2 = carService.addCar(passedCarDTO2)

        val expectedCar1 = Car(1L, "Ford", "Ka", 2010, 12345L, 1)
        val expectedCar2 = Car(1L, "Ford", "Ka", 2010, 34567L, 2)

        assertThat(actualCar1).isEqualTo(expectedCar1)
        assertThat(actualCar2).isEqualTo(expectedCar2)
    }
    @Test
    fun testAddingCarCheckUp() {
        val passedCarCheckUpDTO1 = CarCheckUpDTO("Ante Antić", 1000f, 1)
        val passedCarCheckUpDTO2 = CarCheckUpDTO("Ivo Ivić", 2000f, 1)

        every {
            repository.addCarCheckUp(any())
        } returns true

        val actualCarCheckUp1 = carService.addCheckUp(passedCarCheckUpDTO1)
        val actualCarCheckUp2 = carService.addCheckUp(passedCarCheckUpDTO2)

        val expectedCarCheckUp1 = CarCheckUp("Ante Antić", 1000f, 1, 1)
        val expectedCarCheckUp2 = CarCheckUp("Ivo Ivić", 2000f, 1, 2)

        assertThat(actualCarCheckUp1).isEqualTo(expectedCarCheckUp1)
        assertThat(actualCarCheckUp2).isEqualTo(expectedCarCheckUp2)
    }

    @Test
    fun testFetchingCar() {

        val expectedCar = Car(1L, "Ford", "Ka", 2010, 12345L, 1)
        every {
            repository.getCar(1L)
        } returns expectedCar

        val actualCar = carService.getCar(1L)

        assertThat(actualCar).isEqualTo(expectedCar)
    }
}
