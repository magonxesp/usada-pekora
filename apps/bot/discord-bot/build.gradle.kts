import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

dependencies {
    implementation(project(":contexts:bot"))
    testImplementation(project(":contexts:bot"))
}

application {
    mainClass.set("com.usadapekora.bot.discordbot.MainKt")
}

tasks.withType<ShadowJar>() {
    archiveBaseName.set("discord-bot")
}
