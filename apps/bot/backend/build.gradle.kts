import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val koinVersion = "3.2.0"

plugins {
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("plugin.spring") version "1.7.10"
    application
}

dependencies {
    implementation(project(":contexts:bot"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    testImplementation(project(":contexts:bot"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

application {
    mainClass.set("com.usadapekora.bot.backend.MainKt")
}
