import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val koinVersion = "3.2.0"
val ktorVersion = "2.3.0"

plugins {
    kotlin("jvm") version "1.8.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
    `java-test-fixtures`
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "java-test-fixtures")

    repositories {
        mavenCentral()
        maven {
            setUrl("https://m2.dv8tion.net/releases")
        }
    }

    dependencies {
        val mainDependencies = arrayOf(
            platform("org.jetbrains.kotlin:kotlin-bom"),
            "org.jetbrains.kotlin:kotlin-stdlib-jdk8",
            "com.google.guava:guava:31.0.1-jre",
            "com.discord4j:discord4j-core:3.2.2",
            "com.discord4j:discord4j-rest:3.2.2",
            "com.discord4j:discord4j-voice:3.2.2",
            "com.fasterxml.jackson.module:jackson-module-kotlin",
            "io.ktor:ktor-client-core:$ktorVersion",
            "io.ktor:ktor-client-cio:$ktorVersion",
            "io.ktor:ktor-server-core-jvm:$ktorVersion",
            "io.ktor:ktor-server-netty-jvm:$ktorVersion",
            "io.ktor:ktor-server-status-pages-jvm:$ktorVersion",
            "io.ktor:ktor-server-default-headers-jvm:$ktorVersion",
            "io.ktor:ktor-server-content-negotiation:$ktorVersion",
            "io.ktor:ktor-server-cors:$ktorVersion",
            "io.ktor:ktor-server-auth:$ktorVersion",
            "io.ktor:ktor-server-auth-jwt:$ktorVersion",
            "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion",
            "org.litote.kmongo:kmongo:4.9.0",
            "io.projectreactor.kotlin:reactor-kotlin-extensions",
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4",
            "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4",
            "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1",
            "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0",
            "org.jetbrains.kotlin:kotlin-reflect:1.7.10",
            "com.sedmelluq:lavaplayer:1.3.77",
            "io.github.cdimascio:dotenv-kotlin:6.3.1",
            "io.insert-koin:koin-core:$koinVersion",
            "redis.clients:jedis:4.2.0",
            "ch.qos.logback:logback-core:1.4.7",
            "ch.qos.logback:logback-classic:1.4.7",
            "ch.qos.logback.contrib:logback-json-classic:0.1.5",
            "ch.qos.logback.contrib:logback-jackson:0.1.5",
            "com.fasterxml.jackson.core:jackson-databind:2.15.2",
            "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2",
            "com.fasterxml.jackson.module:jackson-module-kotlin:2.15.1",
            "io.prometheus:simpleclient:0.16.0",
            "io.prometheus:simpleclient_httpserver:0.16.0",
            "io.prometheus:simpleclient_hotspot:0.16.0",
            "io.prometheus:simpleclient_servlet:0.16.0",
            "org.apache.tika:tika-core:2.6.0",
            "io.arrow-kt:arrow-core:1.2.0-RC",
            "com.auth0:java-jwt:4.4.0",
            "com.auth0:jwks-rsa:0.22.0",
            "jakarta.xml.bind:jakarta.xml.bind-api:4.0.0",
            "com.nimbusds:nimbus-jose-jwt:9.31",
            "com.rabbitmq:amqp-client:5.17.0",
            "org.jetbrains.kotlinx:kotlinx-cli:0.3.5"
        )

        val testDependencies = arrayOf(
            kotlin("test"),
            "io.github.serpro69:kotlin-faker:1.11.0",
            "io.mockk:mockk:1.12.7",
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4",
            "io.ktor:ktor-server-test-host:$ktorVersion"
        )

        mainDependencies.forEach {
            implementation(it)
            testFixturesImplementation(it)
        }

        testDependencies.forEach {
            testImplementation(it)
            testFixturesImplementation(it)
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    tasks.test {
        useJUnitPlatform()
        setWorkingDir(System.getProperty("user.dir"))
    }

}
