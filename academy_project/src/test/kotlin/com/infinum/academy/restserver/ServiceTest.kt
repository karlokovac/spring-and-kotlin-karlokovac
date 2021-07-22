package com.infinum.academy.restserver

import com.infinum.academy.restserver.models.CarCheckUpDTO
import com.infinum.academy.restserver.models.CarDTO
import com.infinum.academy.restserver.models.CarWithCheckUps
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.DatabaseCarCheckUpRepository
import com.infinum.academy.restserver.repositories.DatabaseCarRepository
import com.infinum.academy.restserver.services.Service
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ServiceTest {
    private val carRepository = mockk<DatabaseCarRepository>()
    private val carCheckUpRepository = mockk<DatabaseCarCheckUpRepository>()
    private lateinit var carService: Service

    @BeforeEach
    fun setUp() {
        carService = Service(carRepository, carCheckUpRepository)
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

        val actualId = carService.addCheckUp(passedCarCheckUpDTO)

        assertThat(actualId).isEqualTo(0L)
    }

    @Test
    fun testFetchingCar() {
        val expectedCar = CarWithCheckUps(0L, 1L, LocalDate.EPOCH, "Ford", "Ka", 2010, 12345L)
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
