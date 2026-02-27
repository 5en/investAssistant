plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "  "
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.investassistant"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
// 1. 核心：签名配置
    signingConfigs {
        // 正式环境签名（release）
        create("release") {
            // 密钥库文件路径（相对路径：相对于app模块根目录；也可写绝对路径）
            storeFile = file("investAssistant")
            // 密钥库密码（注意：生产环境不要硬编码，下文有安全写法）
            storePassword = "tx19921001"
            // 密钥别名
            keyAlias = "key0"
            // 密钥密码
            keyPassword = "tx19921001"
            // 显式启用V2/V3签名（可选，默认已启用，但显式配置更清晰）
            enableV2Signing = true
            enableV3Signing = true
        }

        // 调试环境签名（debug，可选，Android默认有debug密钥库）
//        create("debug") {
//            // Android默认的debug密钥库路径（无需手动创建）
//            storeFile = file(System.getProperty("user.home") + "/.android/debug.keystore")
//            storePassword = "android"
//            keyAlias = "androiddebugkey"
//            keyPassword = "android"
//        }
    }
    buildTypes {
        // 正式构建类型
        getByName("release") {
            // 绑定release签名配置
            signingConfig = signingConfigs.getByName("release")
            // 可选：开启混淆和资源精简（发布推荐）
            isMinifyEnabled = true
            isShrinkResources = true
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.viewpager2)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // ViewModel + Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
// 本地存储（可选，用 DataStore 存记录）
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // 其他依赖...
    implementation("com.google.code.gson:gson:2.10.1")
// 序列化/反序列化
}