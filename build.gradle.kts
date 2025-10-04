// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.room) apply false
}
configurations.all {
    resolutionStrategy {
        force("androidx.paging:paging-common-jvm:3.3.6")
        force("androidx.paging:paging-runtime:3.3.6")
        force("androidx.paging:paging-compose:3.3.6")
    }
}