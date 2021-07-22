package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.Car
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.sql.Date

@Repository
class DatabaseCarRepository(
    private val jdbcTemplate: JdbcTemplate,
) : CarRepository {
    companion object {
        private const val CAR_INSERT_SQL = "INSERT INTO cars (id,ownerId,dateAdded,manufacturerName,modelName," +
            "productionYear,serialNumber) VALUES (DEFAULT,?,?,?,?,?,?)"
        private const val ownerid_index = 1
        private const val dateAdded_index = 2
        private const val manufacturerName_index = 3
        private const val modelName_index = 4
        private const val productionYear_index = 5
        private const val serialNumber_index = 6
    }

    override fun save(car: Car): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            {
                it.prepareStatement(CAR_INSERT_SQL, arrayOf("id")).apply {
                    setLong(ownerid_index, car.ownerId)
                    setDate(dateAdded_index, Date.valueOf(car.dateAdded))
                    setString(manufacturerName_index, car.manufacturerName)
                    setString(modelName_index, car.modelName)
                    setInt(productionYear_index, car.productionYear)
                    setLong(serialNumber_index, car.serialNumber)
                }
            },
            keyHolder
        )
        return keyHolder.key as Long? ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }

    override fun findById(id: Long): Car {
        try {
            return jdbcTemplate.queryForObject(
                sql = "SELECT * FROM cars WHERE id = ?",
                args = arrayOf(id),
                function = { rs, _ ->
                    Car(
                        rs.getLong("id"), rs.getLong("ownerId"), rs.getDate("dateAdded").toLocalDate(),
                        rs.getString("manufacturerName"), rs.getString("modelName"),
                        rs.getInt("productionYear"), rs.getLong("serialNumber")
                    )
                }
            )
        } catch (ex: EmptyResultDataAccessException) {
            println(ex.message)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}
