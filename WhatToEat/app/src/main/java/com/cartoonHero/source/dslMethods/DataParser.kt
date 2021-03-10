package com.cartoonHero.source.dslMethods

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

inline fun <reified T> convertModelToJson(model: T) : String {
    val jsonAdapter =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            .adapter(T::class.java)
    return jsonAdapter.toJson(model)
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