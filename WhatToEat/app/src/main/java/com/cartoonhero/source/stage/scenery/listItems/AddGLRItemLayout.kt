package com.cartoonhero.source.stage.scenery.listItems

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.cartoonhero.source.actors.dataManger.EditTextItem
import com.cartoonhero.source.actors.dataManger.ItemContentType
import com.cartoonhero.source.actors.dataManger.TextViewItem
import com.cartoonhero.source.actors.toolMan.inlineCls.toDp
import kotlinx.android.synthetic.main.layout_lr_item.view.*

class AddGLRItemLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LRItemLayout(context, attrs, defStyleAttr) {

    var itemHeight = 0
    fun buildLayout() {
        this.leftLayout.setBackgroundColor(Color.parseColor("#F5FFFA"))
        createLeft()
        createRight()
    }
    private fun createLeft() {
        when(itemTemplate?.leftInterface?.contentType) {
            ItemContentType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, LayoutSide.Left)
                textView.layoutParams.width = 0
                textView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                val data = itemTemplate?.leftInterface as TextViewItem
                textView.text = data.text
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                textView.setTextColor(
                    Color.parseColor("#4A4A4A"))
            }
            else -> {}
        }
    }
    private fun createRight() {
        when(itemTemplate?.rightInterface?.contentType) {
            ItemContentType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, LayoutSide.Left)
                val data = itemTemplate?.leftInterface as TextViewItem
                textView.text = data.text
                textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                textView.setTextColor(
                    Color.parseColor("#4A4A4A"))
            }
            ItemContentType.EditText -> {
                val editText = EditText(context)
                buildConstraints(editText,LayoutSide.Right)
                editText.layoutParams.width = 0
                editText.layoutParams.height = 0
                val data = itemTemplate?.rightInterface as EditTextItem
                editText.hint = data.hint
            }
            else -> {}
        }
    }
}