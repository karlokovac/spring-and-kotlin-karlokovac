package com.infinum.academy.courseService.courseRepositoryImpl

import com.infinum.academy.courseService.CourseRepository
import com.infinum.academy.courseService.Course
import com.infinum.academy.courseService.DataSource

class InMemoryCourseRepository(dataSource: DataSource): CourseRepository {

    private val courses = mutableMapOf<Long, Course>()

    init {
        println(dataSource)
    }

    override fun insert(name: String): Long {
        val id = (courses.keys.maxOrNull() ?: 0) + 1
        courses[id] = Course(id, name)
        return id
    }
    override fun findById(id: Long): Course {
        return courses[id] ?: throw CourseNotFoundException(id)
    }
    override fun deleteById(id: Long): Course {
        return courses.remove(id) ?: throw CourseNotFoundException(id)
    }
}
class CourseNotFoundException(id: Long): RuntimeException("Course with and ID $id not found")