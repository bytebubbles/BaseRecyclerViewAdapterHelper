import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    signing
}

val versionName = "4.0.1-m4"


android {
    compileSdk = 31

    defaultConfig {
        minSdk = 16
        targetSdk = 30
    }


    buildTypes {
        getByName("release") {
            consumerProguardFiles("proguard-rules.pro")
        }
    }


    compileOptions {
        kotlinOptions.freeCompilerArgs = ArrayList<String>().apply {
            add("-module-name")
            add("com.github.CymChad.brvah")
            add("-Xjvm-default=all")
        }
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    //namespace = "com.chad.library"

}


dependencies {
    implementation("androidx.annotation:annotation:1.4.0")

    implementation("androidx.recyclerview:recyclerview:1.3.0")

    implementation("androidx.databinding:databinding-runtime:4.2.2")
}

val localProperties: File = project.rootProject.file("local.properties")

if (localProperties.exists()) {
    println("Found secret props file, loading props")
    val properties = Properties()

    InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
        properties.load(reader)
    }


} else {
    println("No props file, loading env vars")
}


afterEvaluate {

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.findByName("release"))
                groupId = "io.github.cymchad"
                artifactId = "BaseRecyclerViewAdapterHelper"
                version = versionName
            }

        }
    }

}