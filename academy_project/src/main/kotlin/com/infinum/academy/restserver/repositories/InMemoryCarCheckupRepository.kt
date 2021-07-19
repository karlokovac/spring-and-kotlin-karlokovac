package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarCheckUp
import org.springframework.stereotype.Component

@Component
class InMemoryCarCheckupRepository (
    private val carRepository: Repository<Long, Car>
) : Repository<Long,CarCheckUp> {
    private val carCheckUps = mutableMapOf<Long, CarCheckUp>()

    override fun save(model: CarCheckUp): Long {
        carRepository.findById(model.carId)?.also {
            it.carCheckUps.add(model)
        } ?: throw CarNotInRepository(model.carId)
        carCheckUps[model.id] = model
        return model.id
    }

    override fun findById(id: Long): CarCheckUp? {
        return carCheckUps[id]
    }

}

class CarNotInRepository(id: Long) : RuntimeException ("Car with $id doesn't exist")