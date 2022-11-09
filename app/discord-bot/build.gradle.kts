import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val koinVersion = "3.2.0"

plugins {
    application
}

dependencies {
    implementation(project(":context"))
    testImplementation(project(":context"))
}

application {
    mainClass.set("com.usadapekora.AppKt")
}
