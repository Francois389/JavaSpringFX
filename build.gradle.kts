plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
    id("java-library")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("maven-publish")
    id("signing")
    id("com.gradleup.nmcp") version "0.1.4"
}

group = "io.github.francois389"
version = "0.1.0"

repositories {
    mavenCentral()
}

val springBootVersion = "4.0.5"

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    api("org.springframework.boot:spring-boot-starter")
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
    withJavadocJar()
}

javafx {
    version = "26"
    modules("javafx.controls", "javafx.fxml")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set("JavaSpringFX")
                description.set("Librairie Kotlin intégrant Spring Boot et JavaFX pour le développement d'applications desktop MVVM")
                url.set("https://github.com/Francois389/JavaSpringFX")

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }

                developers {
                    developer {
                        id.set("francois389")
                        name.set("François")
                        email.set("francois-sp@gmx.fr")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Francois389/JavaSpringFX.git")
                    developerConnection.set("scm:git:ssh://github.com/Francois389/JavaSpringFX.git")
                    url.set("https://github.com/Francois389/JavaSpringFX")
                }
            }
        }
    }

    nmcp {
        centralPortal {
            username = project.findProperty("mavenCentralUsername") as String
            password = project.findProperty("mavenCentralPassword") as String
            publishingType = "USER_MANAGED"
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}
