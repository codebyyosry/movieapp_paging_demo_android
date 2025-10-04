import com.android.build.api.variant.BuildConfigField
import java.io.StringReader
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.room)
}

android {
    namespace = "com.codebyyosry.samples.apps.core.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

    }

    buildFeatures{
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin{
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
        }
    }
}

room {
    // Specify the directory where Room should save the database schema files.
    // This resolves the "No matching Room schema directory" error.
    schemaDirectory("$projectDir/schemas")
}

dependencies {

    implementation(projects.core.domain)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // WorkManager - Kotlin + Coroutines
    implementation(libs.androidx.work.runtime.ktx)
    androidTestImplementation(libs.androidx.work.testing)
    // Dagger Hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.dagger.hilt.work)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // OkHttp logging/interceptor
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.androidx.paging.runtime) // NO EXCLUDE NEEDED
    implementation(libs.androidx.paging.compose) // NO EXCLUDE NEEDED
    implementation(libs.androidx.room.paging) // NO EXCLUDE NEEDED

}

//val backendUrl = providers.fileContents(
//    isolated.rootProject.projectDirectory.file("local.properties")
// Load BACKEND_URL from local.properties (Gradle 8+, Kotlin DSL)
val backendUrl = providers.fileContents(
    project.layout.file(project.provider { rootProject.file("local.properties") })
).asText.map { text ->
    val properties = Properties()
    properties.load(StringReader(text))
    if (properties.containsKey("BACKEND_URL"))
        (properties["BACKEND_URL"] as String)
    else "http://example.com"
    // Move to returning `properties["BACKEND_URL"] as String?` after upgrading to Gradle 9.0.0
}.orElse("http://example.com")


val apiKey = providers.fileContents(
    project.layout.file(project.provider { rootProject.file("local.properties") })
).asText.map { text ->
    val properties = Properties()
    properties.load(StringReader(text))
    if (properties.containsKey("API_KEY"))
        (properties["API_KEY"] as String)
    else ""
    // Move to returning `properties["BACKEND_URL"] as String?` after upgrading to Gradle 9.0.0
}.orElse("")


androidComponents {
    onVariants {
        it.buildConfigFields?.put("BACKEND_URL", backendUrl.map { value ->
            BuildConfigField(type = "String", value = """"$value"""", comment = null)
        })
        it.buildConfigFields?.put("API_KEY", apiKey.map { value ->
            BuildConfigField(type = "String", value = """"$value"""", comment = null)
        })
    }
}