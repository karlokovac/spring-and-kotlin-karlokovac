package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarCheckUp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.sql.Timestamp
import javax.sql.DataSource

@Repository
class DatabaseCarCheckUpRepository(
    private val jdbcTemplate: JdbcTemplate,
    dataSource: DataSource
) : CarCheckUpRepository {
    private val simpleJdbcInsert = SimpleJdbcInsert( dataSource)
        .withTableName("carcheckups")
        .usingGeneratedKeyColumns("id")

    override fun save(carCheckUp: CarCheckUp): Long {
        return try {
            simpleJdbcInsert.executeAndReturnKey(
                mapOf(
                    "workername" to carCheckUp.workerName,
                    "price" to carCheckUp.price,
                    "carid" to carCheckUp.carId,
                    "datetime" to carCheckUp.dateTime
                )
            ).toLong()
        }catch (ex: DataIntegrityViolationException) {
            println(ex.message)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun findAllByCarId(id: Long): List<CarCheckUp> {
        return try {
            jdbcTemplate.queryForList("SELECT * FROM carcheckups WHERE carid = ? ORDER BY datetime DESC", id)
                .map { row ->
                    CarCheckUp(
                        row["id"] as Long,
                        row["workername"] as String,
                        row["price"] as Double,
                        row["carid"] as Long,
                        (row["datetime"] as Timestamp).toLocalDateTime()
                    )
                }
        } catch (ex: EmptyResultDataAccessException) {
            println(ex.message)
            emptyList()
        }
    }
}
