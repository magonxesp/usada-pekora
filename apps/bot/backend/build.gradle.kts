val koinVersion = "3.2.0"

plugins {
    application
}

dependencies {
    implementation(project(":contexts:bot"))
    implementation(project(":contexts:shared"))

    testImplementation(project(":contexts:bot"))
}

application {
    mainClass.set("com.usadapekora.bot.backend.MainKt")
}
