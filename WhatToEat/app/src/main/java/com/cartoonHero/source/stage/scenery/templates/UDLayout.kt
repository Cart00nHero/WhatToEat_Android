package com.cartoonHero.source.stage.scenery.templates

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonHero.source.actors.toolMan.match
import com.cartoonHero.source.props.enities.UDTemplate
import com.cartoonHero.source.props.toDp
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.layout_ud_item.view.*

open class UDLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class UpDownSide {
        Up,Down
    }
    lateinit var template: UDTemplate
    init {
        inflate(context, R.layout.layout_ud_item, this)
    }
    open fun initializeLayout() {
        when {
            template.upViewItem == null ->
                this.ud_top_layout.visibility = View.GONE
            template.downViewItem == null ->
                this.ud_bottom_layout.visibility = View.GONE
        }
        if (template.downViewItem != null) {
            val height = template.bottomHeight
            this.ud_bottom_layout.layoutParams.height = height.toDp(context)
        }
    }
    fun buildConstraints(content: View, side: UpDownSide) {
        when(side) {
            UpDownSide.Up -> {
                this.ud_top_layout.removeAllViews()
                content.id = View.generateViewId()
                this.ud_top_layout.addView(content)
                val set = ConstraintSet()
                set.clone(this.ud_top_layout)
                set.match(content, this.ud_top_layout)
                // optionally, apply the constraints smoothly
                TransitionManager.beginDelayedTransition(this)
                set.applyTo(this.ud_top_layout)
            }
            UpDownSide.Down -> {
                this.ud_bottom_layout.removeAllViews()
                content.id = View.generateViewId()
                this.ud_bottom_layout.addView(content)
                val set = ConstraintSet()
                set.clone(this.ud_bottom_layout)
                set.match(content, this.ud_bottom_layout)
                TransitionManager.beginDelayedTransition(this)
                set.applyTo(this.ud_bottom_layout)
            }
        }
    }
}