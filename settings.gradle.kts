pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}


rootProject.name = "WallX"
include(":app")
include(":core")
include(":core-ui")
include(":features:home")
include(":features:photo-list")
include(":features:photo-detail")
