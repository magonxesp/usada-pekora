plugins {
    application
}

dependencies {
    implementation(project(":context"))
    testImplementation(project(":context"))
}

application {
    mainClass.set("com.usadapekora.discordbot.MainKt")
}
