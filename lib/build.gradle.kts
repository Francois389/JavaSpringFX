plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.openjfx.javafxplugin")
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.gradleup.nmcp") version "0.1.4"
}

group = "io.github.francois389"

version = System.getenv("VERSION")
    ?: runCatching {
        val tag = providers.exec {
            commandLine("git", "describe", "--tags", "--exact-match")
        }.standardOutput.asText.get().trim()
        tag.removePrefix("v")
    }.getOrElse { "0.0.0-SNAPSHOT" }

repositories {
    mavenCentral()
}

val springBootVersion = "4.0.6"

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
}


nmcp {
    centralPortal {
        username = providers.gradleProperty("mavenCentralUsername").orNull
        password = providers.gradleProperty("mavenCentralPassword").orNull
        publishingType = "USER_MANAGED"
    }
}

signing {
    val signingKey = providers.gradleProperty("signingInMemoryKey").orNull
    val signingPassword = providers.gradleProperty("signingInMemoryKeyPassword").orNull
    if (signingKey != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
    sign(publishing.publications["maven"])
}

tasks.register<Exec>("runTestApp") {
    description = "Lance l'application de test située dans le dossier 'test-app'"
    workingDir("test-app")
    commandLine("./gradlew", "run")
    dependsOn("build")
}