package com.infinum.academy.restserver.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${car-details-service.base-url}") val baseUrl: String
) {

    @Bean
    fun webClient(webClientBuilder: WebClient.Builder): WebClient {
        return webClientBuilder
            .baseUrl(baseUrl)
            .build()
    }
}
