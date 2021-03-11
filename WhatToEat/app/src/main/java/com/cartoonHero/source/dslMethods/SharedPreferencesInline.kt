package com.cartoonHero.source.dslMethods

import android.content.SharedPreferences

inline fun SharedPreferences.applyEdit(
    func: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    this.edit().func().apply()
}