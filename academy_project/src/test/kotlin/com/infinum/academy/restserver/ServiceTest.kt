package com.infinum.academy.restserver

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.DatabaseCarCheckUpRepository
import com.infinum.academy.restserver.repositories.DatabaseCarRepository
import com.infinum.academy.restserver.services.CarCheckUpService
import com.infinum.academy.restserver.services.CarService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ServiceTest {
    private val carRepository = mockk<DatabaseCarRepository>()
    private val carCheckUpRepository = mockk<DatabaseCarCheckUpRepository>()
    private lateinit var carService: CarService
    private lateinit var carCheckUpService: CarCheckUpService

    @BeforeEach
    fun setUp() {
        carService = CarService(carRepository, carCheckUpRepository)
        carCheckUpService = CarCheckUpService(carCheckUpRepository)
    }

    @Test
    fun testAddingCar() {
        val passedCarDTO = CarDTO(1L, "Ford", "Ka", 2010, 12345L)

        val car = passedCarDTO.toDomainModel()

        every {
            carRepository.save(car)
        } returns 1

        val actualId = carService.addCar(passedCarDTO)

        assertThat(actualId).isEqualTo(1L)
    }
    @Test
    fun testAddingCarCheckUp() {
        val passedCarCheckUpDTO = CarCheckUpDTO("Ante Antić", 1000.00, 1)

        every {
            carCheckUpRepository.save(any())
        } returns 0

        val actualId = carCheckUpService.addCheckUp(passedCarCheckUpDTO)

        assertThat(actualId).isEqualTo(0L)
    }

    @Test
    fun testFetchingCar() {
        val expectedCar = Car(1L, LocalDate.EPOCH, "Ford", "Ka", 2010, 12345L, 0)
        every {
            carRepository.findById(0L)
        } returns expectedCar
        every {
            carCheckUpRepository.findAllByCarId(0L)
        } returns listOf()
        val actualCar = carService.getCarWithCheckUps(0L)

        assertThat(actualCar).isEqualTo(expectedCar)
    }
}
