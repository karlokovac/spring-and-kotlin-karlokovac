package com.infinum.academy.restserver.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.SecurityFilterChain

private const val USER = "SCOPE_USER"
private const val ADMIN = "SCOPE_ADMIN"

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { }
            csrf { disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeRequests {
                authorize(HttpMethod.POST, "/cars", hasAnyAuthority(ADMIN, USER))
                authorize(HttpMethod.GET, "/cars", hasAuthority(ADMIN))
                authorize(HttpMethod.GET, "/cars/saved-models", permitAll)
                authorize(HttpMethod.GET, "/cars/*", hasAnyAuthority(ADMIN, USER))
                authorize(HttpMethod.DELETE, "/cars/*", hasAuthority(ADMIN))
                authorize(HttpMethod.GET, "/cars/*/checkups", hasAnyAuthority(ADMIN, USER))
                authorize(HttpMethod.POST, "/checkups", hasAuthority(ADMIN))
                authorize(HttpMethod.GET, "/checkups/recent", hasAuthority(ADMIN))
                authorize(HttpMethod.GET, "/checkups/upcoming", hasAuthority(ADMIN))
                authorize(HttpMethod.DELETE, "/checkups/*", hasAuthority(ADMIN))
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                jwt {}
            }
        }
        return http.build()
    }
}
