plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.hilt.android.get().pluginId)
    id("kotlin-kapt")
}

android {
    namespace = "com.chaudhry.najeeb.fetch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.chaudhry.najeeb.fetch"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

//    packaging {
//        jniLibs {
//            useLegacyPackaging = true
//        }
//    }

    kapt {
        arguments {
            arg("kapt.kotlin.generated", file("build/generated/source/kaptKotlin"))
        }
        javacOptions {
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED")
            option("--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED")
        }
    }
}

dependencies {
    implementation(libs.activity)
    implementation(libs.activity.ktx)
    implementation(libs.constraintlayout)
    kapt(libs.room.compiler)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlin.stdlib)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.hilt.android)
    implementation(libs.coroutines.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}