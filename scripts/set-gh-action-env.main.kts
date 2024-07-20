#!/usr/bin/env kotlin

@file:DependsOn("eu.jrie.jetbrains:kotlin-shell-core:0.2.1")
@file:DependsOn("org.slf4j:slf4j-simple:2.0.13")
@file:DependsOn("com.squareup.okio:okio-jvm:3.9.0")
@file:CompilerOptions("-Xopt-in=kotlin.RequiresOptIn")
@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

import okio.FileSystem
import okio.Path.Companion.toPath

fun setEnv(key: String, value: String) {
    val fileSystem = FileSystem.SYSTEM
    val githubEnvPath = System.getenv("GITHUB_ENV").toPath()
    val currentEnvContents = mutableListOf<String>()
    fileSystem.read(githubEnvPath) {
        while (true) {
            val line: String = readUtf8Line() ?: break
            currentEnvContents.add(line)
        }
    }

    fileSystem.write(githubEnvPath) {
        currentEnvContents.forEach {
            writeUtf8(it)
            writeUtf8("\n")
        }

        writeUtf8("$key=$value")
        writeUtf8("\n")
    }
}

