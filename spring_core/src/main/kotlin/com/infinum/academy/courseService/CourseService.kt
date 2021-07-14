package com.infinum.academy.courseService

import org.springframework.stereotype.Component

@Component
class CourseService(
    private val courseRepository: CourseRepository
){
    fun findCourseById(id: Long): Course{
        return courseRepository.findById(id)
    }
    fun deleteCourseById(id: Long): Course{
        return courseRepository.deleteById(id)
    }
    fun insertCourse(courseName: String): Long{
        return courseRepository.insert(courseName)
    }
}