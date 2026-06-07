import org.gradle.kotlin.dsl.dependencies

plugins {
    java
    application
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.openjfx.javafxplugin")
    id("org.springframework.boot") version "4.0.6"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("io.github.francois389.javaspringfx_testapp.Launcher")
}

kotlin {
    jvmToolchain(25)
}

javafx {
    version = "26"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation(project(":lib"))
}