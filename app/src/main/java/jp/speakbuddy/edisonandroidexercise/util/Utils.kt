package jp.speakbuddy.edisonandroidexercise.util

fun isLongFact(fact: String, lengthThreshold: Int = 100): Boolean {
    return fact.length > lengthThreshold
}

fun containsCats(fact: String): Boolean {
    return fact.contains("cats", ignoreCase = true)
}