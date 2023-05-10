import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val koinVersion = "3.2.0"

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenCentral()
        maven {
            setUrl("https://m2.dv8tion.net/releases")
        }
    }

    dependencies {
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.google.guava:guava:31.0.1-jre")
        implementation("com.discord4j:discord4j-core:3.2.2")
        implementation("com.discord4j:discord4j-voice:3.2.2")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.ktor:ktor-client-core:2.0.3")
        implementation("io.ktor:ktor-client-cio:2.0.3")
        implementation("org.litote.kmongo:kmongo:4.6.1")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
        implementation("com.sedmelluq:lavaplayer:1.3.77")
        implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
        implementation("io.insert-koin:koin-core:$koinVersion")
        implementation("redis.clients:jedis:4.2.0")
        implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
        implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")
        implementation("io.prometheus:simpleclient:0.16.0")
        implementation("io.prometheus:simpleclient_httpserver:0.16.0")
        implementation("io.prometheus:simpleclient_hotspot:0.16.0")
        implementation("io.prometheus:simpleclient_servlet:0.16.0")
        implementation("org.apache.tika:tika-core:2.6.0")
        implementation("io.arrow-kt:arrow-core:1.2.0-RC")

        testImplementation(kotlin("test"))
        testImplementation("io.github.serpro69:kotlin-faker:1.11.0")
        testImplementation("io.mockk:mockk:1.12.7")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "18"
    }

    tasks.test {
        useJUnitPlatform()
    }

}
