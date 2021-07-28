package com.infinum.academy.restserver

import com.infinum.academy.restserver.models.AddCarCheckUpDTO
import com.infinum.academy.restserver.models.AddCarDTO
import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarDetails
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import com.infinum.academy.restserver.repositories.CarDetailsRepository
import com.infinum.academy.restserver.repositories.CarRepository
import com.infinum.academy.restserver.services.CarCheckUpService
import com.infinum.academy.restserver.services.CarDetailsValidationService
import com.infinum.academy.restserver.services.CarService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import java.time.LocalDate

class ServiceTest {
    private val carRepository = mockk<CarRepository>()
    private val carCheckUpRepository = mockk<CarCheckUpRepository>()
    private val carDetailsRepository = mockk<CarDetailsRepository>()

    private lateinit var carService: CarService
    private lateinit var carCheckUpService: CarCheckUpService
    private lateinit var carDetailsValidationService: CarDetailsValidationService

    @BeforeEach
    fun setUp() {
        carDetailsValidationService = CarDetailsValidationService(carDetailsRepository)
        carService = CarService(carRepository, carCheckUpRepository, carDetailsRepository, carDetailsValidationService)
        carCheckUpService = CarCheckUpService(carCheckUpRepository)
    }

    @Test
    fun testAddingCar() {
        val passedCarDTO = AddCarDTO(1L, "Ford", "Ka", 2010, 12345L)

        val car = passedCarDTO.toDomainModel(1)

        every {
            carRepository.save(car).id
        } returns 1
        every {
            carDetailsRepository.findByManufacturerNameAndModelName("Ford", "Ka")
        } returns CarDetails("Ford", "Ka", true, 1)

        val actualId = carService.addCar(passedCarDTO)

        assertThat(actualId).isEqualTo(1L)
    }

    @Test
    fun addingSameSerialThrows() {
        val passedCarDTO = AddCarDTO(1L, "Ford", "Ka", 2010, 12345L)

        every {
            carRepository.save(any())
        } throws DataIntegrityViolationException("")

        assertThatThrownBy {
            carService.addCar(passedCarDTO)
        }
    }

    @Test
    fun fetchingNonExistingThrows() {
        every {
            carRepository.findById(any())
        } throws EmptyResultDataAccessException(1)

        assertThatThrownBy {
            carService.getCar(1)
        }
    }

    @Test
    fun testAddingCarCheckUp() {
        val passedCarCheckUpDTO = AddCarCheckUpDTO("Ante Antić", 1000.00, 1)

        every {
            carCheckUpRepository.save(any()).id
        } returns 1L

        val actualId = carCheckUpService.addCheckUp(passedCarCheckUpDTO)

        assertThat(actualId).isEqualTo(1L)
    }

    @Test
    fun testFetchingCar() {
        val expectedCar = Car(1L, LocalDate.EPOCH, 1, 2010, 12345L, 0)
        every {
            carRepository.findById(0L)
        } returns expectedCar
        every {
            carCheckUpRepository.findByCarIdOrderByDateTimeDesc(0L)
        } returns listOf()
        every {
            carDetailsRepository.findById(1)
        } returns CarDetails("Ford", "Ka", true, 1)
        val actualCar = carService.getCar(0L)

        assertThat(actualCar.serialNumber).isEqualTo(12345L)
    }
}
