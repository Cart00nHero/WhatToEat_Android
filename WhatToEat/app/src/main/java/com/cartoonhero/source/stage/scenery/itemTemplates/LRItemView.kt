package com.cartoonhero.source.stage.scenery.itemTemplates

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonhero.source.whattoeat.R

class LRItemView: ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?,
                attributeSetId: Int) : super(context, attrs, attributeSetId)

    private val mView: View = LayoutInflater.from(context)
        .inflate(R.layout.rvitem_lr_itemview,this,false)

    init {
        mView.id = View.generateViewId()
        addView(mView)
        val set = ConstraintSet()
        set.clone(this)
        set.match(mView,this)
    }


    private fun ConstraintSet.match(view: View, parentView: View) {
        this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
        this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
        this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
        this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
    }
}