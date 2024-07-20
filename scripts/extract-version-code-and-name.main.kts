#!/usr/bin/env kotlin
@file:CompilerOptions("-Xopt-in=kotlin.RequiresOptIn")
@file:DependsOn("com.squareup.okio:okio-jvm:3.9.0")

import okio.FileSystem
import okio.Path.Companion.toPath

val appBuildGradlePath: okio.Path = "app/build.gradle.kts".toPath()
val fileSystem = FileSystem.SYSTEM

var versionCode = ""
var versionName = ""

fileSystem.read(appBuildGradlePath) {
    while (true) {
        val line: String = readUtf8Line() ?: break
        if ("versionCode" in line) {
            versionCode = line.trim().split(" ").last()
            println(line.trim().split(" "))
        } else if ("versionName" in line) {
            versionName = line.trim().split(" ")
                .last()
                .replace("\"", "")
            println(line.trim().split(" "))
        }
    }
}

val envPath: okio.Path = ".github/workflows/version.env".toPath()
fileSystem.write(envPath) {
    writeUtf8("VERSION_CODE=$versionCode")
    writeUtf8("\n")

    writeUtf8("VERSION_NAME=$versionName")
    writeUtf8("\n")
}

