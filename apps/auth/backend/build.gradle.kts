plugins {
    application
}

dependencies {
    implementation(project(":contexts:auth"))
    implementation(project(":contexts:shared"))
    implementation("io.ktor:ktor-server-cors-jvm:2.3.0")
    testImplementation(project(":contexts:auth"))
}

application {
    mainClass.set("com.usadapekora.auth.backend.MainKt")
}
