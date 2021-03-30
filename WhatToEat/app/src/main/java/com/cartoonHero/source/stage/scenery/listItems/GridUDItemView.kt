package com.cartoonHero.source.stage.scenery.listItems

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import com.cartoonHero.source.props.enities.ImageViewItem
import com.cartoonHero.source.props.enities.TextViewItem
import com.cartoonHero.source.props.enities.ViewType
import com.cartoonHero.source.props.toDp
import com.cartoonHero.source.stage.scenery.templates.UDLayout

class GridUDItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : UDLayout(context, attrs, defStyleAttr) {
    override fun initializeLayout() {
        super.initializeLayout()
        buildLayout()
    }
    private fun buildLayout() {
        createTop()
        createBottom()
    }

    private fun createTop() {
        when(template.upViewItem?.viewType) {
            ViewType.ImageView -> {
                val data = template.upViewItem as ImageViewItem
                val imageView = ImageView(context)
                buildConstraints(imageView, UpDownSide.Up)
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.setImageResource(data.imageDrawable)
            }
            else -> {}
        }
    }
    private fun createBottom() {
        when(template.downViewItem?.viewType) {
            ViewType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, UpDownSide.Down)
                val data = template.downViewItem as TextViewItem
                textView.text = data.text
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,data.textSize)
                textView.textAlignment = data.alignment
                textView.setTextColor(data.textColor)
                (textView.layoutParams as LayoutParams).apply {
                    marginStart = 10.toDp(context)
                }
            }
            else -> {}
        }
    }
}