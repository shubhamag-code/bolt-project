plugins {
    kotlin("jvm") version "2.1.0"
    application
    kotlin("plugin.serialization") version "1.9.21"
}

group = "com.bolt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.bolt.config.MainApplicationKt")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.7")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.7")

    implementation("io.ktor:ktor-client-core-jvm:2.3.7")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.7")

    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("ch.qos.logback:logback-classic:1.4.11")

    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("io.ktor:ktor-client-mock:2.3.4")

}

tasks.test {
    useJUnitPlatform()
}
