package com.infinum.academy.courseService

import com.infinum.academy.courseService.courseRepositoryImpl.CourseNotFoundException
import com.infinum.academy.courseService.courseRepositoryImpl.InMemoryCourseRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(InMemoryConfiguration::class)
class CourseServiceInMemory @Autowired constructor(
    private val applicationContext: ApplicationContext,
    private val courseService: CourseService,
) {
    @Test
    @DisplayName("Verify beans")
    fun verifyBeans() {
        Assertions.assertThat(
            applicationContext.getBean<CourseService>()
        ).isNotNull
    }

    @Test
    @DisplayName("should return course")
    fun retrieveInserted() {
        val service = applicationContext.getBean<CourseService>()
        service.insertCourse("Android")
        service.insertCourse("ios")
        Assertions.assertThat(
            courseService.findCourseById(2L)
        ).isEqualTo(
            Course(2,"ios")
        )
    }

    @Test
    @DisplayName("should return course")
    fun deleteCourse() {
        val service = applicationContext.getBean<CourseService>()
        Assertions.assertThat(
            courseService.findCourseById(2L)
        ).isEqualTo(
            Course(2L,"ios")
        )
        service.deleteCourseById(2L)

        Assertions.assertThatThrownBy{
            courseService.findCourseById(2L)
        }.isInstanceOf(CourseNotFoundException::class.java)
            .hasMessage("Course with and ID 2 not found")
    }
}

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class InMemoryConfiguration{
    @Bean
    fun courseRepository(dataSource: DataSource): CourseRepository {
        return InMemoryCourseRepository(dataSource)
    }
}
