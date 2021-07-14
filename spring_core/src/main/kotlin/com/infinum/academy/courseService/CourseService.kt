package com.infinum.academy.courseService

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class CourseService(
    /**Switch between:
     *  -in-memory
     *  -in-file
     */
    @Qualifier("in-file") private val courseRepository: CourseRepository
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