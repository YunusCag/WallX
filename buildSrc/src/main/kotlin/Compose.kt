object Compose {
    private const val activityComposeVersion = "1.13.0"
    private const val composeBOMVersion = "2026.08.00"
    private const val constraitLayoutVersion = "1.1.2"
    private const val material3Version = "1.4.0"
    private const val pagingVersion = "3.5.1"
    private const val pagingComposeVersion = "3.5.1"
    private const val coilVersion = "2.7.0"
    private const val blurHashVersion = "1.0.3"
    private const val splashVersion = "1.2.0"
    // Last published version of the standalone icon artifact; material3 does not
    // bring it in, and it used to arrive transitively through accompanist-pager.
    private const val materialIconsVersion = "1.7.8"

    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
    const val composeBOM = "androidx.compose:compose-bom:$composeBOMVersion"
    const val composeUI = "androidx.compose.ui:ui"
    const val composeUIGraphic = "androidx.compose.ui:ui-graphics"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val material3 = "androidx.compose.material3:material3:$material3Version"
    const val materialWindowSizeClass = "androidx.compose.material3:material3-window-size-class"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:$constraitLayoutVersion"
    const val materialIcons = "androidx.compose.material:material-icons-core:$materialIconsVersion"

    const val pagingRuntime = "androidx.paging:paging-runtime:$pagingVersion"
    const val pagingCompose = "androidx.paging:paging-compose:$pagingComposeVersion"

    const val coil = "io.coil-kt:coil-compose:$coilVersion"

    const val blurHash = "com.github.dalafiarisamuel:composeblurhash:$blurHashVersion"

    const val splash = "androidx.core:core-splashscreen:$splashVersion"

    // Test Dependencies
    const val composeUITestJUnit = "androidx.compose.ui:ui-test-junit4"
    const val composeTestUITooling = "androidx.compose.ui:ui-tooling"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest"
}
