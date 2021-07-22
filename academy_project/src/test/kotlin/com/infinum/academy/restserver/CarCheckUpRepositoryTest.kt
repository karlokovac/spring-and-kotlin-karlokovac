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

const val CARCHECKUP_TEST_SQL = "INSERT INTO carcheckups (id,workername,price,carid,datetime)" +
        " VALUES (DEFAULT,'Pero Perić',:price,:carid,CURRENT_TIMESTAMP)"
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarCheckUpRepositoryTest {

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
            CARCHECKUP_TEST_SQL,
            mapOf(
                "carid" to 1,
                "price" to 2000
            )
        )
        jdbcTemplate.update(
            CARCHECKUP_TEST_SQL,
            mapOf(
                "carid" to 1,
                "price" to 3000
            )
        )
    }

    @Test
    fun fetchFirstCarCheckUp() {
        Assertions.assertThat(
            jdbcTemplate.queryForObject(
                "SELECT carid FROM carcheckups WHERE id = :id",
                mapOf("id" to 1L),
                Long::class.java
            )
        ).isEqualTo(1L)
    }
}
