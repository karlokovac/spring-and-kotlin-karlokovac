package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.CarCheckUp
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import javax.sql.DataSource

@Repository
class DatabaseCarCheckUpRepository(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate,
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
        return namedParameterJdbcTemplate.query(
            "SELECT * FROM carcheckups WHERE carid = :carid ORDER BY datetime DESC",
            mapOf("carid" to id)
        ) { rs, _ ->
            CarCheckUp(
                rs.getLong("id"),
                rs.getString("workername"),
                rs.getDouble("price"),
                rs.getLong("carid"),
                rs.getTimestamp("datetime").toLocalDateTime()
            )
        }
    }
}
