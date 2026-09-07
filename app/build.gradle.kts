plugins {
    alias(libs.plugins.android.application)
}

android {
    val clasificacionPreview = providers.gradleProperty("clasificacionPreview").orElse("false").get().toBoolean()
    buildFeatures { buildConfig = true }
    namespace = "com.example.appecolim"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.appecolim"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("boolean", "CLASIFICACION_PREVIEW", clasificacionPreview.toString())
        }
        release {
            buildConfigField("boolean", "CLASIFICACION_PREVIEW", "false")
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
