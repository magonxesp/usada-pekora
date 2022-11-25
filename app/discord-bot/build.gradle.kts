import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

dependencies {
    implementation(project(":context"))
    testImplementation(project(":context"))
}

application {
    mainClass.set("com.usadapekora.discordbot.MainKt")
}

tasks.withType<ShadowJar>() {
    archiveBaseName.set("discord-bot")
}
