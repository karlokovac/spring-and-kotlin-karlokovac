package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarCheckUp
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.sql.Timestamp

const val CARCHECKUP_INSERT_SQL =
    "INSERT INTO carCheckUps (id,workername,price,carid,datetime) VALUES (DEFAULT,?,?,?,?)"


@Repository
class DatabaseCarCheckUpRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun save(carCheckUp: CarCheckUp): Long {
        return try {
            val keyHolder = GeneratedKeyHolder()
            jdbcTemplate.update({
                it.prepareStatement(CARCHECKUP_INSERT_SQL, arrayOf("id")).apply {
                    setString(1, carCheckUp.workerName)
                    setDouble(2, carCheckUp.price)
                    setLong(3, carCheckUp.carId)
                    setTimestamp(4, Timestamp.valueOf(carCheckUp.dateTime))
                }
            }, keyHolder)
            keyHolder.key as Long? ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }catch (ex: DataIntegrityViolationException){
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    fun findAllByCarId(id: Long): List<CarCheckUp> {
        return try {
            jdbcTemplate.queryForList("SELECT * FROM carcheckups WHERE carid = ?", id)
                .map { row ->
                    CarCheckUp(
                        row["id"] as Long,
                        row["workername"] as String,
                        row["price"] as Double,
                        row["carid"] as Long,
                        (row["datetime"] as Timestamp).toLocalDateTime()
                    )
                }.sortedByDescending { carCheckUp -> carCheckUp.dateTime }
        } catch (ex: EmptyResultDataAccessException) {
            emptyList()
        }
    }
}
