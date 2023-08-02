plugins {
    id(Plugins.androidApplication)
    id(Plugins.androidKotlin)
    id(Plugins.kotlinKapt)
}

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycle)
    implementation(AndroidX.composeViewModel)
    implementation(AndroidX.composeNavigation)
    implementation(AndroidX.hiltNavigationCompose)


    implementation(Compose.activityCompose)
    implementation(platform(Compose.composeBOM))
    implementation(Compose.composeUI)
    implementation(Compose.composeUIGraphic)
    implementation(Compose.composeToolingPreview)
    implementation(Compose.material3)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)


    testImplementation(TestLibs.jUnit)
    androidTestImplementation(TestLibs.jUnitExt)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(platform(Compose.composeBOM))
    androidTestImplementation(Compose.composeUITestJUnit)
    debugImplementation(Compose.composeTestUITooling)
    debugImplementation(Compose.composeTestManifest)
}