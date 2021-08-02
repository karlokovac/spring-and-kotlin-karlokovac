package com.infinum.academy.restserver

import com.infinum.academy.restserver.models.CarDetailsDTO
import com.infinum.academy.restserver.services.HttpCarDataService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType
import org.mockserver.springtest.MockServerTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@MockServerTest
@SpringBootTest
@AutoConfigureMockMvc
class HttpCarDetailsTest @Autowired constructor(
    private val httpCarDataService: HttpCarDataService
) {

    lateinit var mockServerClient: MockServerClient

    @Test
    fun test() {
        mockServerClient
            .`when`(
                HttpRequest.request()
                    .withPath("/api/v1/cars")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(
                        """
                        {
                            "data":[
                                {
                                    "manufacturer":"Abarth",
                                    "model_name":"1000",
                                    "is_common":0
                                },
                                {
                                    "manufacturer":"Abarth",
                                    "model_name":"1000 Bialbero",
                                    "is_common":0
                                 },
                                {
                                    "manufacturer":"Abarth",
                                    "model_name":"1000 GT",
                                    "is_common":0
                                }
                            ]
                        }
                        """.trimIndent()
                    )
            )

        assertThat(
            httpCarDataService.getAllCarData()
        ).isEqualTo(
            listOf(
                CarDetailsDTO("Abarth", "1000", false),
                CarDetailsDTO("Abarth", "1000 Bialbero", false),
                CarDetailsDTO("Abarth", "1000 GT", false)
            )
        )
    }
}
