plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
    id("java-library")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("maven-publish")
}

group = "io.github.francois389"
version = "0.1.0"

repositories {
    mavenCentral()
}


val springBootVersion = "4.0.5"

dependencies {
    // BOM Spring Boot pour aligner toutes les versions
    api(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

    api("org.springframework.boot:spring-boot-starter")
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
}

javafx {
    version = "26"
    modules("javafx.controls", "javafx.fxml")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}