plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies{
    implementation(libs.javax.inject)
//    api(libs.androidx.paging.common.jvm)
    compileOnly(libs.androidx.paging.common.jvm)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)

}