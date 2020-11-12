package com.cartoonhero.source.stage.scenery.listItems

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonhero.source.actors.toolMan.match
import com.cartoonhero.source.whattoeat.R
import kotlinx.android.synthetic.main.layout_lr_item.view.*

class LRItemLayout: ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    // if use @JvmOverloads, use this constructor only
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
        super(context, attrs, defStyleAttr)

    init {
        // get the inflater service from the android system
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // inflate the layout into "this" component
        inflater.inflate(R.layout.layout_lr_item, this)
    }

    fun createTextView() {
        this.leftLayout.removeAllViews()
        val textView = TextView(context)
        textView.id = View.generateViewId()
        this.leftLayout.addView(textView)
        val set = ConstraintSet()
        set.clone(this.leftLayout)
        set.match(textView,this.leftLayout)
        set.applyTo(this.leftLayout)
    }
}