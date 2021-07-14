package com.infinum.academy.courseService

interface CourseRepository {
    fun insert(name: String): Long
    fun findById(id: Long): Course
    fun deleteById(id: Long): Course
}