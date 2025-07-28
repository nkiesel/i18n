plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.versions.update)
}

group = "ord.nkiesel"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.datetime)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
