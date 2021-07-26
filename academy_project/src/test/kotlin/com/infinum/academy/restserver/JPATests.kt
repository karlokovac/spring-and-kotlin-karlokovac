package com.infinum.academy.restserver

import com.infinum.academy.restserver.models.CarCheckUp
import com.infinum.academy.restserver.models.StoredCarDTO
import com.infinum.academy.restserver.repositories.CarCheckUpRepository
import com.infinum.academy.restserver.repositories.CarRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable
import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JPATests @Autowired constructor (
    val carRepository: CarRepository,
    val carCheckUpRepository: CarCheckUpRepository
) {

    var carId: Long = 0
    var checkUpId: Long = 0

    @BeforeEach
    fun setup() {
        val car1 = StoredCarDTO(1L, LocalDate.now(), "Porsche", "911 GT3", 2020, 11L)
        val car2 = StoredCarDTO(1L, LocalDate.now(), "Porsche", "Taycan", 2020, 22L)
        carId = carRepository.save(car1).id
        carRepository.save(car2)

        checkUpId = carCheckUpRepository.save(CarCheckUp("Ivo Ivić", 12000.0, carId)).id
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
