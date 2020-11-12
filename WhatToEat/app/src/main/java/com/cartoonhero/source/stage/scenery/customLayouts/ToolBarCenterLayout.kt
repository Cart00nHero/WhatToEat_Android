package com.cartoonhero.source.stage.scenery.customLayouts

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonhero.source.actors.toolMan.match
import com.cartoonhero.source.whattoeat.R

class ToolBarCenterLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_toolbar_center, this)
    }

    fun createTextView(): TextView {
        this.removeAllViews()
        val textView = TextView(context)
        textView.id = View.generateViewId()
        textView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        textView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        textView.isSingleLine = true
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        this.addView(textView)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.match(textView, this)
        // optionally, apply the constraints smoothly
        TransitionManager.beginDelayedTransition(this)
        // both blocks will put the check box exact center of parent
        // finally, apply the constraint set to constraint layout
        constraintSet.applyTo(this)
        return textView
    }
}