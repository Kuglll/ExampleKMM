plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.6.21"
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    ios()

    val ktorVersion = "2.0.2"
    val koinVersion = "3.1.5"
    val sqlDriver = "1.5.3"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("com.russhwolf:multiplatform-settings:0.9")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.insert-koin:koin-test:$koinVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
                implementation("com.squareup.sqldelight:android-driver:$sqlDriver")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDriver")
            }
            dependsOn(commonMain)
        }
        val iosTest by getting {
            dependsOn(commonTest)
        }
    }
}

android {
    namespace = "com.example.gettingstartedwithkmm"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}


/**
You'll also use the new memory manager for Kotlin/Native, which will soon become the default.
Add the following at the end of the build.gradle.kts file:
 */
kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
    }
}

sqldelight {
    database("OrganizeDb") {
        packageName = "com.example.gettingstartedwithkmm"
        schemaOutputDirectory = file("src/commonMain/sqldelight/com/example/gettingstartedwithkmm/db")
    }
}