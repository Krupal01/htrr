package com.htr.htrr.presentation.navigation

import android.util.Base64
import kotlinx.serialization.json.Json

fun Any.toNavArg(): String =
    Base64.encodeToString(toString().toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)

fun String.fromNavArg(): String =
    String(Base64.decode(this, Base64.URL_SAFE or Base64.NO_WRAP))


inline fun <reified T> T.toJsonNavArg(): String {
    val json = Json.encodeToString(this)
    return Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
}

inline fun <reified T> String.fromJsonNavArg(): T {
    val decodedJson = String(Base64.decode(this, Base64.URL_SAFE or Base64.NO_WRAP))
    return Json.decodeFromString(decodedJson)
}