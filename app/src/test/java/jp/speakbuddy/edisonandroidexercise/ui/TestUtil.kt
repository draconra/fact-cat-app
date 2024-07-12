package jp.speakbuddy.edisonandroidexercise.ui

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun <T> StateFlow<T>.getOrAwaitValue(): T = runBlocking {
    first()
}