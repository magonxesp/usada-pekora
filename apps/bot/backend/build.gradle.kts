import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val koinVersion = "3.2.0"

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

dependencies {
    implementation(project(":contexts:bot"))
    implementation(project(":contexts:shared"))
    implementation("io.ktor:ktor-server-auth-jvm:2.3.0")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.3.0")

    testImplementation(project(":contexts:bot"))
}

application {
    mainClass.set("com.usadapekora.bot.backend.MainKt")
}

tasks.withType<ShadowJar>() {
    archiveBaseName.set("backend")
}
