import io.ktor.plugin.features.*

val ktorVersion: String by project
val kotlinWrappersVersion: String by project

val kotlinxCoroutinesVersion: String by project
val kotlinxSerializationVersion: String by project
val kotlinxHtmlVersion: String by project

plugins {
    kotlin("multiplatform") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("io.ktor.plugin") version "2.3.8"
}

group = "gay.extremist"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Unnecessary if using react router (without jury-rigging)
ktor {
    docker {
        localImageName.set("mosaic")
        imageTag.set("latest")
        jreVersion.set(JavaVersion.VERSION_17)
        portMappings.set(listOf(DockerPortMapping(insideDocker = 8080, outsideDocker = 80)))
    }
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        binaries.executable()
    }

    // Not necessary if using react router
    jvm {
        withJava()

        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-html:$kotlinxHtmlVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-auth:$ktorVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))

                // Wrappers
                implementation(project.dependencies.enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")

                // Ktor
                implementation("io.ktor:ktor-client-js:$ktorVersion")

                // NPM
                implementation(npm("dashjs", "4.7.4"))
            }
        }

        // Not necessary if using react router
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
//                implementation("org.jetbrains.exposed:exposed-core:0.40.1")
//                implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
//                implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
            }
        }

        // Not necessary if using react router
        applyDefaultHierarchyTemplate()
    }
}