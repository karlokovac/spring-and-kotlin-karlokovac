package com.infinum.academy.courseService

import com.infinum.academy.courseService.courseRepositoryImpl.CourseNotFoundException
import com.infinum.academy.courseService.courseRepositoryImpl.InFileCourseRepository
import com.infinum.academy.courseService.courseRepositoryImpl.InMemoryCourseRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource

fun main() {
    val appContext = AnnotationConfigApplicationContext(
        ApplicationConfiguration::class.java
    )

    val service = appContext.getBean<CourseService>()
    service.insertCourse("Kotlin & Spring")
    service.insertCourse("ios")
    service.insertCourse("javascript")
    service.insertCourse("RubyOnRails")
    service.insertCourse("Android")
    for (i in 1..5)
        println( service.findCourseById(i.toLong()) )
    println()
    service.deleteCourseById(2L)
    service.deleteCourseById(4L)

    for (i in 1..5) {
        try {
            println(service.findCourseById(i.toLong()))
        }
        catch (exception: CourseNotFoundException){
            println("EXCEPTION: ${exception.message}")
        }
    }
}

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class ApplicationConfiguration(
    @Value("\${spring.datasource.persistence}") private val persistence: Boolean
){
    @Bean
    fun courseRepository(dataSource: DataSource, resource: Resource): CourseRepository{
        return when(persistence){
            true -> InFileCourseRepository( resource )
            false -> InMemoryCourseRepository( dataSource )
        }
    }

    @Bean
    fun resource(dataSource: DataSource): Resource{
        return FileSystemResource(dataSource.dbName)
    }
}