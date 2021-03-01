package com.cartoonHero.source.inlineMethods
import android.app.Activity
import android.content.Intent
internal inline fun <reified T : Activity> Activity.startActivity(inInitializer: Intent.() -> Unit) {
  startActivity(
    Intent(this,T::class.java).apply(inInitializer)
  )
}