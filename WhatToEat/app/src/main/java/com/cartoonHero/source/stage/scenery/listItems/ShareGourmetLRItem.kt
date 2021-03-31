package com.cartoonHero.source.stage.scenery.listItems

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cartoonHero.source.props.enities.*
import com.cartoonHero.source.props.*
import com.cartoonHero.source.redux.actions.ItemEditTextDidChangedAction
import com.cartoonHero.source.redux.actions.ViewOnClickAction
import com.cartoonHero.source.redux.appStore
import com.cartoonHero.source.stage.scenery.templates.LRLayout
import kotlinx.android.synthetic.main.layout_lr_item.view.*

class ShareGourmetLRItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LRLayout(context, attrs, defStyleAttr) {
    private val concatPosition = ConcatPosition()
    override fun initializeLayout() {
        super.initializeLayout()
        buildLayout()
    }
    fun setConcatPosition(section:Int, position: Int) {
        concatPosition.section = section
        concatPosition.position = position
    }
    fun concatPosition():ConcatPosition {
        return concatPosition
    }
    private fun buildLayout() {
        this.leftLayout.setBackgroundColor(Color.parseColor("#FFF8DC"))
        this.rightLayout.setBackgroundColor(Color.parseColor("#FFF8DC"))
        createLeft()
        createRight()
    }
    private fun createLeft() {
        when(template.leftViewItem?.viewType) {
            ViewType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, LeftRightSide.Left)
                textView.layoutParams.width = 0
                textView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                val data = template.leftViewItem as TextViewItem
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
    private fun createRight() {
        when(template.rightViewItem?.viewType) {
            ViewType.TextView -> {
                val textView = TextView(context)
                buildConstraints(textView, LeftRightSide.Right)
                val data = template.rightViewItem as TextViewItem
                textView.text = data.text
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,data.textSize)
                textView.textAlignment = data.alignment
                textView.setTextColor(data.textColor)
                (textView.layoutParams as LayoutParams).apply {
                    marginStart = 10.toDp(context)
                }
            }
            ViewType.EditText -> {
                val editText = EditText(context)
                buildConstraints(editText,LeftRightSide.Right)
                editText.layoutParams.width = 0
                editText.layoutParams.height = 0
                val data = template.rightViewItem as EditTextItem
                editText.hint = data.hint
                editText.inputType = data.inputType
                editText.imeOptions = EditorInfo.IME_ACTION_DONE
                editText.setUnderlineColor(
                    Color.parseColor("#704214"))
                editText.setCursorColor(Color.parseColor("#704214"))
                editText.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int,
                        count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int,
                        before: Int, count: Int) {
                        appStore.dispatch(
                            ItemEditTextDidChangedAction(this@ShareGourmetLRItem,s.toString()))
                    }
                    override fun afterTextChanged(s: Editable?) {
                    }
                })
            }
            ViewType.Button -> {
                val button = Button(context)
                buildConstraints(button,LeftRightSide.Right)
                val data = template.rightViewItem as ButtonItem
                button.text = data.title
                button.setTextColor(data.textColor)
                button.setBackgroundColor(data.backgroundColor)
                button.layoutParams.width = 0
                button.layoutParams.height = 0
                (button.layoutParams as LayoutParams).apply {
                    marginStart = 20.toDp(context)
                    marginEnd = 20.toDp(context)
                    topMargin = 10.toDp(context)
                    bottomMargin = 10.toDp(context)
                }
                button.setOnClickListener {
                    appStore.dispatch(ViewOnClickAction(it))
                }
            }
            else -> {}
        }
    }
}