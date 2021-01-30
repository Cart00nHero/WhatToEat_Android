package com.cartoonHero.source.actors.toolMan.inlineCls

import android.content.SharedPreferences

inline fun SharedPreferences.applyEdit(func: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    this.edit().func().apply()
}