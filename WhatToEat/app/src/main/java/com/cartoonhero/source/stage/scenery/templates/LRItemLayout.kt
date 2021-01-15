package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.os.Build
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonhero.source.actors.dataManger.LRTemplate
import com.cartoonhero.source.actors.toolMan.inlineCls.toDp
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

    lateinit var template: LRTemplate

    init {
        inflate(context, R.layout.layout_lr_item, this)
    }

    open fun initializeLayout() {
        when {
            template.leftInterface == null ->
                this.leftLayout.visibility = View.GONE
            template.rightInterface == null ->
                this.rightLayout.visibility = View.GONE
        }
        if (template.leftInterface != null) {
            val width = template.leftLayoutWidth
            this.leftLayout.layoutParams.width = width.toDp(context)
        }
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(this)
                }
                set.applyTo(this.leftLayout)
            }
            LayoutSide.Right -> {
                this.rightLayout.removeAllViews()
                content.id = View.generateViewId()
                this.rightLayout.addView(content)
                val set = ConstraintSet()
                set.clone(this.rightLayout)
                set.match(content, this.rightLayout)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(this)
                }
                set.applyTo(this.rightLayout)
            }
        }
    }
}