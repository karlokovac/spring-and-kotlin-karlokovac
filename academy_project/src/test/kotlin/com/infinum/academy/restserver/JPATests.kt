package com.infinum.academy.restserver

import com.infinum.academy.restserver.models.CarCheckUpEntity
import com.infinum.academy.restserver.models.CarDetailsEntity
import com.infinum.academy.restserver.models.CarEntity
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import com.infinum.academy.restserver.repositories.CarDetailsRepository
import com.infinum.academy.restserver.repositories.CarRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JPATests @Autowired constructor (
    val carRepository: CarRepository,
    val carCheckUpRepository: CarCheckUpRepository,
    val carDetailsRepository: CarDetailsRepository
) {

    var carId: Long = 0
    var checkUpId: Long = 0

    @BeforeEach
    fun setup() {
        val carDetailsEntity1 = carDetailsRepository.save(CarDetailsEntity("Porsche", "911 GT3", false, 1))
        val carDetailsEntity2 = carDetailsRepository.save(CarDetailsEntity("Porsche", "Taycan", false, 2))
        val carEntity1 = CarEntity(1L, LocalDate.now(), carDetailsEntity1, 2020, 11L)
        val carEntity2 = CarEntity(1L, LocalDate.now(), carDetailsEntity2, 2020, 22L)
        carId = carRepository.save(carEntity1).id
        carRepository.save(carEntity2)

        checkUpId = carCheckUpRepository.save(CarCheckUpEntity("Ivo Ivić", 12000.0, carId, LocalDateTime.now())).id
    }

    @Test
    fun canFetchFirstCar() {
        val firstCar = carRepository.findById(1)
        assertThat(firstCar.serialNumber).isEqualTo(11L)
    }

    @Test
    fun canFetchAllCars() {
        val page = carRepository.findAll(Pageable.ofSize(10))
        assertThat(page.numberOfElements).isEqualTo(2)
    }

    @Test
    fun canFetchCarCheckUpDetailsForCar() {
        val page = carCheckUpRepository.findByCarId(carId, Pageable.ofSize(10))
        assertThat(page.numberOfElements).isEqualTo(1)
    }
}
