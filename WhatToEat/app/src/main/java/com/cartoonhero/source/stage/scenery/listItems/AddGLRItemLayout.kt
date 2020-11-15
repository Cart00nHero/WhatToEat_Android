package com.cartoonhero.source.stage.scenery.listItems

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonhero.source.actors.dataManger.ItemContentType
import com.cartoonhero.source.actors.dataManger.TextViewItem
import com.cartoonhero.source.actors.toolMan.match
import kotlinx.android.synthetic.main.layout_lr_item.view.*

class AddGLRItemLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LRItemLayout(context, attrs, defStyleAttr) {


    // MARK: - build content
    fun buildItem() {
        createLeft()
        createRight()
    }
    private fun createLeft() {
        when(itemTemplate?.leftInterface?.contentType) {
            ItemContentType.TextView -> {
                val textView = TextView(context)
                buildItemContent(textView,ContentSide.Left)
                val data = itemTemplate?.leftInterface as TextViewItem
                textView.text = data.text
                textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
//                textView.setTextColor(
//                        Color.parseColor("#4A4A4A"))
            }
            else -> {}
        }
    }
    private fun createRight() {
        when(itemTemplate?.rightInterface?.contentType) {
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