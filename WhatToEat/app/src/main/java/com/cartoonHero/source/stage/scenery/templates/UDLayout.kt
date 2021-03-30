package com.cartoonHero.source.stage.scenery.templates

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.cartoonHero.source.props.enities.UDTemplate
import com.cartoonHero.source.props.toDp
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.layout_lr_item.view.*
import kotlinx.android.synthetic.main.layout_ud_item.view.*

class UDLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class LayoutSide {
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
    fun buildConstraints() {
        
    }
}