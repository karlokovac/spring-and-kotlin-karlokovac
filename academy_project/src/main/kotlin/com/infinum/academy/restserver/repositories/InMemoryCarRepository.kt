package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.Car
import org.springframework.stereotype.Component

@Component
class InMemoryCarRepository : Repository<Long, Car> {
    private val cars = mutableMapOf<Long, Car>()

    override fun save(model: Car): Long {
        cars[model.id] = model
        return model.id
    }

    override fun findById(id: Long): Car? {
        return cars[id]?.also { it.carCheckUps.sortBy { carCheckUp -> carCheckUp.date } }
    }
}
