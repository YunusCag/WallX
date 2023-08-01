object Compose {
    private const val activityComposeVersion = "1.7.2"
    private const val composeBOMVersion = "2023.03.00"

    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
    const val composeBOM = "androidx.compose:compose-bom:$composeBOMVersion"
    const val composeUI = "androidx.compose.ui:ui"
    const val composeUIGraphic = "androidx.compose.ui:ui-graphics"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val material3 = "androidx.compose.material3:material3"

    // Test Dependencies
    const val composeUITestJUnit = "androidx.compose.ui:ui-test-junit4"
    const val composeTestUITooling = "androidx.compose.ui:ui-tooling"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest"
}