#!/usr/bin/env kotlin
@file:DependsOn("com.squareup.okio:okio-jvm:3.9.0")
@file:CompilerOptions("-Xopt-in=kotlin.RequiresOptIn")
@file:Import("set-gh-action-env.main.kts")

import okio.FileSystem
import okio.Path.Companion.toPath

val fileSystem = FileSystem.SYSTEM
val affectedModulePath = ".github/workflows/version.env".toPath()
fileSystem.read(affectedModulePath) {
    while (true) {
        val line: String = readUtf8Line() ?: break
        println("env value: $line")
        val envValues = line.split("=")
        setEnv(envValues[0], envValues[1])
    }
}