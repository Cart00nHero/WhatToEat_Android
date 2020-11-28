package com.cartoonhero.source.stage.scenery.listItems

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.cartoonhero.source.actors.dataManger.EditTextItem
import com.cartoonhero.source.actors.dataManger.HorizontalTextViewsItem
import com.cartoonhero.source.actors.dataManger.ItemContentType
import com.cartoonhero.source.actors.dataManger.TextViewItem
import com.cartoonhero.source.actors.toolMan.inlineCls.toDp
import com.cartoonhero.source.stage.scenery.templates.LRItemLayout
import com.cartoonhero.source.whattoeat.R
import kotlinx.android.synthetic.main.layout_lr_item.view.*

class AddGLRItemLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LRItemLayout(context, attrs, defStyleAttr) {

    fun buildLayout() {
        this.leftLayout.setBackgroundColor(Color.parseColor("#F5FFFA"))
        createLeft()
        createRight()
    }
    private fun createLeft() {
        when(template?.leftInterface?.contentType) {
            ItemContentType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, LayoutSide.Left)
                textView.layoutParams.width = 0
                textView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                val data = template?.leftInterface as TextViewItem
                textView.text = data.text
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                textView.setTextColor(
                    Color.parseColor("#4A4A4A"))
            }
            else -> {}
        }
    }
    private fun createRight() {
        when(template?.rightInterface?.contentType) {
            ItemContentType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, LayoutSide.Left)
                val data = template?.leftInterface as TextViewItem
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
                val data = template?.rightInterface as EditTextItem
                editText.hint = data.hint
            }
            ItemContentType.HorizontalTextViews -> {
                val hzlView = LayoutInflater.from(context).inflate(
                    R.layout.layout_horizontal_textviews,this,false)
                buildConstraints(hzlView, LayoutSide.Right)
                val data = template?.rightInterface as HorizontalTextViewsItem
                val textViews = ArrayList<TextView>()
                for (item in data.textItems) {
                    val textView = TextView(context)
                    (hzlView as LinearLayout).addView(textView)
                    val layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1.0F
                    )
                    textView.layoutParams = layoutParams
                    textView.setPadding(10.toDp(context),10.toDp(context),
                        10.toDp(context),10.toDp(context))
                    textView.text = item.text
                    textView.setTextSize(
                        TypedValue.COMPLEX_UNIT_SP,
                        16.0f)
                    textView.textAlignment = item.alignment
                    textView.setTextColor(item.textColor)
                    textViews.add(textView)
                }
            }
            else -> {}
        }
    }
}