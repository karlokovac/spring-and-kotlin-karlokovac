package com.infinum.academy.courseService

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig


@SpringJUnitConfig(ApplicationConfiguration::class)
class UserServiceIntegrationTest @Autowired constructor(
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
    fun test() {
        val service = applicationContext.getBean<CourseService>()
        service.insertCourse("Android")
        service.insertCourse("ios")
        Assertions.assertThat(
            courseService.findCourseById(2)
        ).isEqualTo(
            Course(2,"ios")
        )
    }

}