import java.util.Properties

// Release signing is read from local.properties (never committed). Values there are
// plain strings - do NOT wrap them in quotes like the BuildConfig entries.
val keystoreProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}

fun keystoreProperty(name: String): String? =
    keystoreProperties.getProperty(name)?.takeIf { it.isNotBlank() }

val releaseSigningConfigured = listOf(
    "WALLX_STORE_FILE", "WALLX_STORE_PASSWORD", "WALLX_KEY_ALIAS", "WALLX_KEY_PASSWORD"
).all { keystoreProperty(it) != null }

plugins {
    id(Plugins.androidApplication)
    id(Plugins.androidKotlin)
    id(Plugins.kotlinCompose)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
    id(Plugins.googleServices)
    id(Plugins.crashlytics)
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

    signingConfigs {
        if (releaseSigningConfigured) {
            create("release") {
                storeFile = file(keystoreProperty("WALLX_STORE_FILE")!!)
                storePassword = keystoreProperty("WALLX_STORE_PASSWORD")
                keyAlias = keystoreProperty("WALLX_KEY_ALIAS")
                keyPassword = keystoreProperty("WALLX_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // null when the keystore is not configured: an unsigned build is far
            // safer than silently shipping a debug-signed release to Play.
            signingConfig = signingConfigs.findByName("release")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    coreLibraryDesugaring(Desugar.jdkLibs)

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

    implementation(WorkManager.coroutineWork)
    implementation(WorkManager.workHilt)


    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(Timber.timber)

    implementation(platform(Firebase.bom))
    implementation(Firebase.analystic)
    implementation(Firebase.crashlytics)

    implementation(Admob.admob)
    implementation(Admob.ump)


    testImplementation(TestLibs.jUnit)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(platform(Compose.composeBOM))
    androidTestImplementation(Compose.composeUITestJUnit)
    debugImplementation(Compose.composeTestUITooling)
    debugImplementation(Compose.composeTestManifest)
}