plugins {
    kotlin("jvm") version "2.1.0"
    application
}

group = "com.bolt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.bolt.MainApplicationKt")
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.2")
    implementation("io.ktor:ktor-server-netty:2.3.2")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")

    implementation("io.ktor:ktor-client-core:2.3.2")
    implementation("io.ktor:ktor-client-cio:2.3.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("ch.qos.logback:logback-classic:1.4.11")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
