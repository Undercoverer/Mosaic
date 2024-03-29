import com.varabyte.kobweb.gradle.application.extensions.AppBlock.LegacyRouteRedirectStrategy
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
    alias(libs.plugins.kotlin.serialization)
}

group = "gay.extremist.mosaic"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }

        // Only legacy sites need this set. Sites built after 0.16.0 should default to DISALLOW.
        // See https://github.com/varabyte/kobweb#legacy-routes for more information.
        legacyRouteRedirectStrategy.set(LegacyRouteRedirectStrategy.DISALLOW)
    }
}

kotlin {
    configAsKobwebApplication("mosaic")

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components)
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.serialization)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk) // This default template uses built-in SVG icons, but what's available is limited.
            implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            implementation(libs.ktor.client.content.negotiation)
            implementation(npm("dashjs", "4.7.4"))
        }
    }
}
