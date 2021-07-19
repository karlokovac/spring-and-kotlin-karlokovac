package com.infinum.academy.courseService

import com.infinum.academy.courseService.courseRepositoryImpl.CourseNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(ApplicationConfiguration::class)
class CourseServiceIntegrationTest @Autowired constructor(
    private val applicationContext: ApplicationContext,
    private val courseService: CourseService,
) {
    @Test
    @DisplayName("should require beans from application context")
    fun verifyBeans() {
        assertThat(
            applicationContext.getBean<CourseService>()
        ).isNotNull
    }

    @Test
    @DisplayName("should return course")
    fun retrieveInserted() {
        val service = applicationContext.getBean<CourseService>()
        service.insertCourse("Android")
        service.insertCourse("ios")
        assertThat(
            courseService.findCourseById(2L)
        ).isEqualTo(
            Course(2,"ios")
        )
    }

    @Test
    @DisplayName("should throw CourseNotFoundException when finding deleted course")
    fun deleteCourse() {
        val service = applicationContext.getBean<CourseService>()
        assertThat(
            courseService.findCourseById(2L)
        ).isEqualTo(
            Course(2L,"ios")
        )
        service.deleteCourseById(2L)

        assertThatThrownBy{
            courseService.findCourseById(2L)
        }.isInstanceOf(CourseNotFoundException::class.java)
            .hasMessage("Course with and ID 2 not found")
    }
}