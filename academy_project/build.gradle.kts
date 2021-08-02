import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
    kotlin("plugin.jpa") version "1.5.10"
    jacoco
    id("io.gitlab.arturbosch.detekt").version("1.18.0-RC2")
    id("org.jlleitschuh.gradle.ktlint").version("10.1.0")
    id("org.unbroken-dome.test-sets") version "4.0.0"
}

group = "com.infinum.academy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

extra["testcontainersVersion"] = "1.15.3"

testSets {
    create("integrationTest")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("net.javacrumbs.shedlock:shedlock-spring:4.24.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:4.24.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mock-server:mockserver-spring-test-listener:5.11.2")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.getByName("check").dependsOn("integrationTest")
tasks.getByName("integrationTest").mustRunAfter("test")

tasks.withType<JacocoReport> {
    // merge unit and integration test reports so coverage is correct
    executionData.setFrom(
        fileTree(buildDir).include("/jacoco/test.exec", "/jacoco/integrationTest.exec")
    )
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
