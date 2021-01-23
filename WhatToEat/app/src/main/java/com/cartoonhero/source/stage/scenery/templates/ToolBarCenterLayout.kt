package com.mitake.base.amSrc.stage.scenery.templates

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonhero.source.actors.toolMan.inlineCls.toDp
import com.cartoonhero.source.actors.toolMan.match
import com.cartoonhero.source.whattoeat.R


class ToolBarCenterLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var centerView: View
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_toolbar_center, this)
    }

    @SuppressLint("NewApi")
    fun createTextView(): TextView {
        this.removeAllViews()
        val textView = TextView(this.context)
        textView.id = View.generateViewId()
        this.addView(textView)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.match(textView, this)
        TransitionManager.beginDelayedTransition(this)
        constraintSet.applyTo(this)
        textView.setTextColor(Color.WHITE)
        textView.isSingleLine = true
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20f)
        textView.setPadding(textView.paddingLeft,textView.paddingTop,
                70.toDp(context),textView.paddingBottom)
        textView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        textView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        centerView = textView
        return textView
    }
    @SuppressLint("NewApi")
    fun barCenterView(): View {
        return centerView
    }
}