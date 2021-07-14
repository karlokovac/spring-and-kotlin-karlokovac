package com.infinum.academy.courseService

import com.infinum.academy.courseService.courseRepositoryImpl.CourseNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CourseServiceTest {
    private val courseRepository = mockk<CourseRepository>()

    private lateinit var courseService: CourseService

    @BeforeEach
    fun setUp() {
        courseService = CourseService(courseRepository)
    }

    @Test
    @DisplayName("should return course")
    fun testIfReturnsValid() {
        val expectedCourse = Course(1,"Spring & Kotlin")
        every {
            courseRepository.findById(any())
        } returns expectedCourse

        val actualCourse = courseService.findCourseById(1)
        Assertions.assertThat(expectedCourse).isEqualTo(actualCourse)
        verify(exactly = 1) { courseRepository.findById(any()) }
    }

    @Test
    @DisplayName("should return first id")
    fun testIfInsertsProperly() {
        val expectedId = 1L
        every {
            courseRepository.insert(any())
        } returns expectedId

        val actualId = courseService.insertCourse("Spring & Kotlin")
        Assertions.assertThat(expectedId).isEqualTo(actualId)
        verify(exactly = 1) { courseRepository.insert(any()) }
    }

    @Test
    @DisplayName("should return deleted course")
    fun testIfDeleteReturnsValid() {
        val expectedCourse = Course(1,"Spring & Kotlin")
        every {
            courseRepository.deleteById(any())
        } returns expectedCourse

        val actualCourse = courseService.deleteCourseById(1)
        Assertions.assertThat(expectedCourse).isEqualTo(actualCourse)
        verify(exactly = 1) { courseRepository.deleteById(any()) }
    }

    @Test
    @DisplayName("should throw not found exception from findById")
    fun passFindException() {
        every {
            courseRepository.findById(1)
        } throws CourseNotFoundException(1)

        Assertions.assertThatThrownBy {
            courseService.findCourseById(1)
        }.isInstanceOf(CourseNotFoundException::class.java)
            .hasMessage("Course with and ID 1 not found")
    }

    @Test
    @DisplayName("should throw not found exception from deleteById")
    fun passDeleteException() {
        every {
            courseRepository.deleteById(1)
        } throws CourseNotFoundException(1)

        Assertions.assertThatThrownBy {
            courseService.deleteCourseById(1)
        }.isInstanceOf(CourseNotFoundException::class.java)
            .hasMessage("Course with and ID 1 not found")
    }
}