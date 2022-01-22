import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("com.jetbrains.exposed.gradle.plugin") version "0.2.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.0"
    application
}

group = "com.github.pprochot.uj.pprochot"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    maven ("https://dl.bintray.com/kotlin/exposed")
}

dependencies {
    implementation("org.jetbrains.exposed:exposed:0.17.14")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("io.ktor:ktor-server-netty:1.6.4")
    implementation("io.ktor:ktor-html-builder:1.6.4")
    implementation("io.ktor:ktor-serialization:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
    implementation("io.ktor:ktor-server-core:1.6.4")
    implementation("io.ktor:ktor-jackson:1.6.4")
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha10")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda:2.12.3")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("ServerKt")
}