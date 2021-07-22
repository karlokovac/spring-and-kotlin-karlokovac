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

@Repository
class DatabaseCarCheckUpRepository(
    private val jdbcTemplate: JdbcTemplate
) : CarCheckUpRepository {
    companion object {
        private const val CARCHECKUP_INSERT_SQL =
            "INSERT INTO carCheckUps (id,workername,price,carid,datetime) VALUES (DEFAULT,?,?,?,?)"
        private const val workerName_index = 1
        private const val price_index = 2
        private const val carid_index = 3
        private const val dateTime_index = 4
    }

    override fun save(carCheckUp: CarCheckUp): Long {
        return try {
            val keyHolder = GeneratedKeyHolder()
            jdbcTemplate.update(
                {
                    it.prepareStatement(CARCHECKUP_INSERT_SQL, arrayOf("id")).apply {
                        setString(workerName_index, carCheckUp.workerName)
                        setDouble(price_index, carCheckUp.price)
                        setLong(carid_index, carCheckUp.carId)
                        setTimestamp(dateTime_index, Timestamp.valueOf(carCheckUp.dateTime))
                    }
                },
                keyHolder
            )
            keyHolder.key as Long? ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        } catch (ex: DataIntegrityViolationException) {
            println(ex.message)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun findAllByCarId(id: Long): List<CarCheckUp> {
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
            println(ex.message)
            emptyList()
        }
    }
}
