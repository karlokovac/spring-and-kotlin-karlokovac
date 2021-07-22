package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.Car
import com.infinum.academy.restserver.models.CarWithCheckUps
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.sql.Date

const val CAR_INSERT_SQL = "INSERT INTO cars (id,ownerId,dateAdded,manufacturerName,modelName,productionYear,serialNumber)" +
        " VALUES (DEFAULT,?,?,?,?,?,?)"

@Repository
class DatabaseCarRepository(
    private val jdbcTemplate: JdbcTemplate,
) {

    fun save(car: Car): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({
            it.prepareStatement (CAR_INSERT_SQL, arrayOf("id")).apply {
                setLong(1, car.ownerId)
                setDate(2, Date.valueOf(car.dateAdded) )
                setString(3, car.manufacturerName)
                setString(4, car.modelName)
                setInt(5, car.productionYear)
                setLong(6,car.serialNumber)
            }
        },keyHolder)
        return keyHolder.key as Long? ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }

    fun findById(id: Long): CarWithCheckUps {
        try {
            return jdbcTemplate.queryForObject(
                sql = "SELECT * FROM cars WHERE id = ?",
                args = arrayOf(id),
                function = { rs, _ ->
                    CarWithCheckUps(
                        rs.getLong("id") ,rs.getLong("ownerId"),rs.getDate("dateAdded").toLocalDate(),
                        rs.getString("manufacturerName"),rs.getString("modelName"),
                        rs.getInt("productionYear"),rs.getLong("serialNumber")
                    )
                }
            )
        }catch (ex: DataAccessException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}
