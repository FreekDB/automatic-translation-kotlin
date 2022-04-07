val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
}

group = "com.github"
version = "0.0.1"
application {
    mainClass.set("com.github.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

val googleTranslationVersion = "2.1.12"
val googleCloudVersion = "25.1.0"

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    platform("com.google.cloud:libraries-bom:$googleCloudVersion")
    implementation("com.google.cloud:google-cloud-translate:$googleTranslationVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
