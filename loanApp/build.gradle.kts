plugins {
    kotlin("jvm") version "1.9.23"
    id("application")
}

group = "org.jory"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-netty:1.6.2")
    implementation("io.ktor:ktor-server-core:1.6.2")
    implementation("io.ktor:ktor-gson:1.6.2")
    implementation("org.jetbrains.exposed:exposed-core:0.34.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.34.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.34.1")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
//    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}