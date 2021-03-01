package com.cartoonHero.source.stage.scenery.listItems

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.cartoonHero.source.enities.EditTextItem
import com.cartoonHero.source.enities.TextViewItem
import com.cartoonHero.source.enities.ViewType
import com.cartoonHero.source.stage.scenery.templates.LRLayout
import kotlinx.android.synthetic.main.layout_lr_item.view.*

class AddGourmetLRItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LRLayout(context, attrs, defStyleAttr) {

    override fun initializeLayout() {
        super.initializeLayout()
        buildLayout()
    }
    private fun buildLayout() {
        this.leftLayout.setBackgroundColor(Color.parseColor("#F5FFFA"))
        createLeft()
        createRight()
    }
    private fun createLeft() {
        when(template.leftViewItem?.viewType) {
            ViewType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, LayoutSide.Left)
                textView.layoutParams.width = 0
                textView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                val data = template.leftViewItem as TextViewItem
                textView.text = data.text
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                textView.setTextColor(
                    Color.parseColor("#4A4A4A"))
            }
            else -> {}
        }
    }
    private fun createRight() {
        when(template.rightViewItem?.viewType) {
            ViewType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, LayoutSide.Right)
                val data = template.rightViewItem as TextViewItem
                textView.text = data.text
                textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                textView.setTextColor(
                    Color.parseColor("#4A4A4A"))
            }
            ViewType.EditText -> {
                val editText = EditText(context)
                buildConstraints(editText,LayoutSide.Right)
                editText.layoutParams.width = 0
                editText.layoutParams.height = 0
                val data = template.rightViewItem as EditTextItem
                editText.hint = data.hint
            }
            else -> {}
        }
    }
}