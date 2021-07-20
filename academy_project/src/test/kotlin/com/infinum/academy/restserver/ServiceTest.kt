package com.infinum.academy.restserver

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.repositories.Repository
import com.infinum.academy.restserver.services.Service
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ServiceTest {
    private val carRepository = mockk<Repository<Long, Car>>()
    private val carCheckUpRepository = mockk<Repository<Long, CarCheckUp>>()
    private lateinit var carService: Service

    @BeforeEach
    fun setUp() {
        carService = Service(carRepository, carCheckUpRepository)
        Car.counter = 1
        CarCheckUp.counter = 1
    }

    @Test
    fun testAddingCar() {
        val passedCarDTO1 = CarDTO(1L, "Ford", "Ka", 2010, 12345L)
        val passedCarDTO2 = CarDTO(1L, "Ford", "Ka", 2010, 34567L)

        val car1 = Car(1L, "Ford", "Ka", 2010, 12345L, 1)
        val car2 = Car(1L, "Ford", "Ka", 2010, 34567L, 2)

        every {
            carRepository.save(car1)
        } returns 1
        every {
            carRepository.save(car2)
        } returns 2

        val actualId1 = carService.addCar(passedCarDTO1)
        val actualId2 = carService.addCar(passedCarDTO2)

        assertThat(actualId1).isEqualTo(1L)
        assertThat(actualId2).isEqualTo(2L)
    }
    @Test
    fun testAddingCarCheckUp() {
        val passedCarCheckUpDTO1 = CarCheckUpDTO("Ante Antić", 1000f, 1)
        val passedCarCheckUpDTO2 = CarCheckUpDTO("Ivo Ivić", 2000f, 1)

        val carCheckUp1 = CarCheckUp("Ante Antić", 1000f, 1, 1)
        val carCheckUp2 = CarCheckUp("Ivo Ivić", 2000f, 1, 2)

        every {
            carCheckUpRepository.save(carCheckUp1)
        } returns 1
        every {
            carCheckUpRepository.save(carCheckUp2)
        } returns 2

        val actualId1 = carService.addCheckUp(passedCarCheckUpDTO1)
        val actualId2 = carService.addCheckUp(passedCarCheckUpDTO2)

        assertThat(actualId1).isEqualTo(1L)
        assertThat(actualId2).isEqualTo(2L)
    }

    @Test
    fun testFetchingCar() {
        val expectedCar = Car(1L, "Ford", "Ka", 2010, 12345L, 1)
        every {
            carRepository.findById(1L)
        } returns expectedCar

        val actualCar = carService.getCar(1L)

        assertThat(actualCar).isEqualTo(expectedCar)
    }
}
