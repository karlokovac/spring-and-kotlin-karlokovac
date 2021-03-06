package com.infinum.academy.courseService.courseRepositoryImpl

import com.infinum.academy.courseService.CourseRepository
import com.infinum.academy.courseService.Course
import org.springframework.core.io.Resource
import java.io.FileOutputStream

class InFileCourseRepository(
    private val coursesFileResource: Resource // will be provided through dependency injection
) : CourseRepository {
    init {
        if (coursesFileResource.exists().not()) {
            coursesFileResource.file.createNewFile()
        }
    }
    override fun insert(name: String): Long {
        val file = coursesFileResource.file
        val id = (file.readLines()
            .filter { it.isNotEmpty() }
            .map { line -> line.split(",").first().toLong() }
            .maxOrNull() ?: 0) + 1
        file.appendText("$id,$name\n")
        return id
    }
    override fun findById(id: Long): Course {
        return coursesFileResource.file.readLines()
            .filter { it.isNotEmpty() }
            .find { line -> line.split(",").first().toLong() == id }
            ?.let { line ->
                val tokens = line.split(",")
                Course(id = tokens[0].toLong(), name = tokens[1])
            }
            ?: throw CourseNotFoundException(id)
    }
    override fun deleteById(id: Long): Course {
        val coursesLines = coursesFileResource.file.readLines()
        var lineToDelete: String? = null
        FileOutputStream(coursesFileResource.file)
            .writer()
            .use { fileOutputWriter ->
                coursesLines.forEach { line ->
                    if (line.split(",").first().toLong() == id) {
                        lineToDelete = line
                    } else {
                        fileOutputWriter.appendLine(line)
                    }
                }
            }
        return lineToDelete?.let { line ->
            val tokens = line.split(",")
            Course(id = tokens[0].toLong(), name = tokens[1])
        } ?: throw CourseNotFoundException(id)
    }
}