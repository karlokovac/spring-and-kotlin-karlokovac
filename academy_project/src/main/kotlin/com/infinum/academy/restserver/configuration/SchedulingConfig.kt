package com.infinum.academy.restserver.configuration

import com.infinum.academy.restserver.models.toCarDetailsDTO
import com.infinum.academy.restserver.models.toDomainModel
import com.infinum.academy.restserver.repositories.CarDetailsRepository
import com.infinum.academy.restserver.services.HttpCarDataService
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.cache.annotation.CacheEvict
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import javax.sql.DataSource

@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
@EnableScheduling
class SchedulingConfig(
    private val httpCarDataService: HttpCarDataService,
    private val carDetailsRepository: CarDetailsRepository
) {

    // @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(fixedDelay = 100_000)
    @SchedulerLock(name = "scheduledTaskName", lockAtLeastFor = "20s")
    @CacheEvict("names", allEntries = true)
    fun updateCarDetails() {
        println("Cleared caches")
        val carDetails = httpCarDataService.getAllCarData()
            ?: throw NoCarsReceivedException()
        val existing = carDetailsRepository.findAll().map { it.toCarDetailsDTO() }
        carDetailsRepository.saveAll(
            carDetails
                .filterNot { details -> existing.contains(details) }
                .map { it.toDomainModel() }
        )
    }

    @Bean
    fun lockProvider(dataSource: DataSource): LockProvider {
        return JdbcTemplateLockProvider(
            JdbcTemplateLockProvider.Configuration.builder()
                .withJdbcTemplate(JdbcTemplate(dataSource))
                .usingDbTime()
                .build()
        )
    }
}

class NoCarsReceivedException : RuntimeException("Error fetching the car details")
