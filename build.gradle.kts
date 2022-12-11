// Add compose gradle plugin
plugins {
    kotlin("multiplatform") version "1.7.20"
    id("org.jetbrains.compose") version "1.3.0-beta04-dev873"
}

// Add maven repositories
repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

val kdsVersion = "2.2.0"
// Enable JS(IR) target and add dependencies
kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation("com.soywiz.korlibs.kds:kds-js:$kdsVersion")
            }
        }

        commonMain {
            dependencies {
                implementation("com.soywiz.korlibs.kds:kds:$kdsVersion")
            }
        }
    }
}