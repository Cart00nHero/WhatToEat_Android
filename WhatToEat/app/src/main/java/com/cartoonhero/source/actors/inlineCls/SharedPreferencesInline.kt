package com.cartoonhero.source.actors.inlineCls

import android.content.SharedPreferences

inline fun SharedPreferences.applyEdit(func: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    this.edit().func().apply()
}