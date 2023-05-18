plugins {
    id("org.springframework.boot") version "3.0.7"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.7.10"
    application
}

dependencies {
    implementation(project(":contexts:auth"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")

    testImplementation(project(":contexts:auth"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

application {
    mainClass.set("com.usadapekora.auth.backend.MainKt")
}
