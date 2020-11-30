package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonhero.source.actors.datamanger.LRTemplate
import com.cartoonhero.source.actors.toolMan.match
import com.cartoonhero.source.whattoeat.R
import kotlinx.android.synthetic.main.layout_lr_item.view.*

open class LRItemLayout: ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    // if use @JvmOverloads, use this constructor only
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
            super(context, attrs, defStyleAttr)

    enum class LayoutSide {
        Left,Right
    }

    var template: LRTemplate? = null

    init {
        inflate(context, R.layout.layout_lr_item, this)
    }

    fun buildConstraints(content: View, side: LayoutSide) {
        when(side) {
            LayoutSide.Left -> {
                this.leftLayout.removeAllViews()
                content.id = View.generateViewId()
                this.leftLayout.addView(content)
                val set = ConstraintSet()
                set.clone(this.leftLayout)
                set.match(content, this.leftLayout)
                // optionally, apply the constraints smoothly
                TransitionManager.beginDelayedTransition(this)
                set.applyTo(this.leftLayout)
            }
            LayoutSide.Right -> {
                this.rightLayout.removeAllViews()
                content.id = View.generateViewId()
                this.rightLayout.addView(content)
                val set = ConstraintSet()
                set.clone(this.rightLayout)
                set.match(content, this.rightLayout)
                TransitionManager.beginDelayedTransition(this)
                set.applyTo(this.rightLayout)
            }
        }
    }
}