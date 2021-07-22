package com.infinum.academy.restserver

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

const val CAR_TEST_SQL = "INSERT INTO cars (id,ownerId,dateAdded,manufacturerName,modelName,productionYear," +
    "serialNumber) VALUES (DEFAULT,:ownerid,CURRENT_TIMESTAMP,'Audi',:model,'2010',11)"

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {

    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @BeforeEach
    fun setUp() {
        jdbcTemplate.update(
            CAR_TEST_SQL,
            mapOf(
                "ownerid" to 1,
                "model" to "R8"
            )
        )
        jdbcTemplate.update(
            CAR_TEST_SQL,
            mapOf(
                "ownerid" to 2,
                "model" to "TT"
            )
        )
    }

    @Test
    fun fetchACar() {
        Assertions.assertThat(
            jdbcTemplate.queryForObject(
                "SELECT DISTINCT manufacturername FROM cars WHERE ownerid = :oid AND modelname = :mn",
                mapOf(
                    "mn" to "R8",
                    "oid" to 1
                ),
                String::class.java
            )
        ).isEqualTo("Audi")
    }

    @Test
    fun testFetchingTwoCarsShouldException() {
        assertThatThrownBy {
            jdbcTemplate.queryForObject(
                "SELECT name FROM cars WHERE manufacturername=:manname",
                mapOf("manname" to "Audi"),
                String::class.java
            )
        }.isInstanceOf(DataAccessException::class.java)
    }
}
