package com.cartoonhero.source.stage.scenery.listItems

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.cartoonhero.source.actors.dataManger.ItemContentType
import com.cartoonhero.source.actors.dataManger.TextViewItem

class AddGLRItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LRItemLayout(context, attrs, defStyleAttr) {


    // MARK: - build content
    private fun createLeft() {
        val type = itemTemplate?.leftInterface?.contentType
        when(type) {
            ItemContentType.TextView -> {
                val textView = TextView(context)
                buildItemContent(textView,ContentSide.Left)
                val data = itemTemplate?.leftInterface as TextViewItem
                textView.text = data.text
                textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                textView.setTextColor(
                        Color.parseColor("#4A4A4A"))
            }
            else -> {}
        }
    }
}