package com.cartoonHero.source.props

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.lang.reflect.Field

fun EditText.setUnderlineColor(color: Int) {
    val colorStateList = ColorStateList.valueOf(color)
    this.backgroundTintList = colorStateList
}

// extension function to set/change edit text cursor color programmatically
fun EditText.setCursorColor(color: Int){
    val editText = this
    val shapeDrawable = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setSize(2.dpToPixels(context), 0)
        setColor(color)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        textCursorDrawable = shapeDrawable
    }else{
        try {
            // get the cursor resource id
            TextView::class.java.getDeclaredField("mCursorDrawableRes").apply {
                isAccessible = true
                val drawableResId: Int = getInt(editText)

                // get the editor
                val editorField: Field = TextView::class.java
                    .getDeclaredField("mEditor")
                editorField.isAccessible = true
                @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                val editor: Any = editorField.get(editText)

                // get the drawable and set a color filter
                val drawable: Drawable? = ContextCompat
                    .getDrawable(editText.context, drawableResId)
                drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)

                // set the drawables
                editor.javaClass.getDeclaredField("mCursorDrawable").apply {
                    isAccessible = true
                    set(editor, arrayOf(drawable,drawable))
                }
            }
        } catch (e: Exception) {
            // log exception here
        }
    }
}
