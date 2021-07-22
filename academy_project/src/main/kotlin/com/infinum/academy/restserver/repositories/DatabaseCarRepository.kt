package com.infinum.academy.restserver.repositories

import com.infinum.academy.restserver.models.Car
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import javax.sql.DataSource

@Repository
class DatabaseCarRepository(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate,
    dataSource: DataSource
) : CarRepository {
    private val simpleJdbcInsert = SimpleJdbcInsert(dataSource)
        .withTableName("cars")
        .usingGeneratedKeyColumns("id")

    override fun save(car: Car): Long {
        return simpleJdbcInsert.executeAndReturnKey(
            mapOf(
                "ownerid" to car.ownerId,
                "dateadded" to car.dateAdded,
                "manufacturername" to car.manufacturerName,
                "modelname" to car.modelName,
                "productionyear" to car.productionYear,
                "serialnumber" to car.serialNumber
            )
        ).toLong()
    }

    override fun findById(id: Long): Car {
        return try {
            namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM cars WHERE id = :id",
                mapOf("id" to id)
            ) { rs, _ ->
                Car(
                    rs.getLong("id"), rs.getLong("ownerId"), rs.getDate("dateAdded").toLocalDate(),
                    rs.getString("manufacturerName"), rs.getString("modelName"),
                    rs.getInt("productionYear"), rs.getLong("serialNumber")
                )
            } ?: throw EmptyResultDataAccessException(1)
        } catch (ex: DataAccessException) {
            println(ex.message)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}
