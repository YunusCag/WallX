plugins {
    id(Plugins.androidApplication)
    id(Plugins.androidKotlin)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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

    implementation(project(Modules.core_ui))
    implementation(project(Modules.home))
    implementation(project(Modules.photoList))
    implementation(project(Modules.photoDetail))
    implementation(project(Modules.search))

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
    implementation(Compose.splash)

    implementation(Accompanist.animatedNavigation)
    implementation(WorkManager.coroutineWork)
    implementation(WorkManager.workHilt)


    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(Timber.timber)
    testImplementation(TestLibs.jUnit)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(platform(Compose.composeBOM))
    androidTestImplementation(Compose.composeUITestJUnit)
    debugImplementation(Compose.composeTestUITooling)
    debugImplementation(Compose.composeTestManifest)
}