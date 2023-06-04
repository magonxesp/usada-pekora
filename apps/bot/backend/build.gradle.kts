val koinVersion = "3.2.0"

plugins {
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
