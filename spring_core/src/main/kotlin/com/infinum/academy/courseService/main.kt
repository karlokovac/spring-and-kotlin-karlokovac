package com.infinum.academy.courseService

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
    for (i in 1..6)
    println( service.findCourseById(i.toLong()) )
}

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class ApplicationConfiguration{
    @Bean
    fun resource(dataSource: DataSource): Resource{
        return FileSystemResource(dataSource.dbName)
    }

}