package com.cartoonHero.source.props

import com.apollographql.apollo.api.Input
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

inline fun <reified T> convertAnyToJson(anyThing: T) : String {
    val jsonAdapter =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            .adapter(T::class.java)
    return jsonAdapter.toJson(anyThing)
}

inline fun <reified T> Map<String,T>.toJson(): String {
    val type = Types.newParameterizedType(Map::class.java, String::class.java, T::class.java)
    val jsonAdapter: JsonAdapter<Map<String, T>> = Moshi.Builder().build().adapter(type)
    return jsonAdapter.toJson(this)
}

inline fun <reified T> String.toAny(): T? {
    val jsonAdapter =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            .adapter(T::class.java)
    return jsonAdapter.fromJson(this)
}
inline fun <reified T> String.toMap(): Map<String,T>? {
    val type = Types.newParameterizedType(Map::class.java, String::class.java, T::class.java)
    val jsonAdapter: JsonAdapter<Map<String,T>> = Moshi.Builder().build().adapter(type)
    return jsonAdapter.fromJson(this)
}
inline fun <reified T> Input<T>.toMap(): Map<String,Any> {
    val inputMap = mutableMapOf<String,Any>()
    if (this.value != null) {
        inputMap["value"] = this.value!!
    }
    inputMap["defined"] = this.defined
    return inputMap
}

fun isoNationCodeToLocale(isoCode: String): Locale {
    return when (isoCode) {
        "CN" -> Locale.CHINA
        "HK" -> Locale.CHINESE
        "JP" -> Locale.JAPAN
        "KR" -> Locale.KOREA
        "TW" -> Locale.TAIWAN
        else -> Locale.ENGLISH
    }
}