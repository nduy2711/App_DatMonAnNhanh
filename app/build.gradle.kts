plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.nhom10"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nhom10"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Thêm Glide
    implementation("com.github.bumptech.glide:glide:4.15.0") // Kiểm tra phiên bản mới nhất trên trang Glide
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.0") // Nếu bạn sử dụng Glide 4.x

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
