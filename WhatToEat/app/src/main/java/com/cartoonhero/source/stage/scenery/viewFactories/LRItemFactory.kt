package com.cartoonhero.source.stage.scenery.viewFactories

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonhero.source.actors.datamanger.lrItemData.ItemDataInterface

class LRItemFactory(
    itemData: ItemDataInterface, left: ConstraintLayout, right: ConstraintLayout
) {

    private val mLeftLayout: ConstraintLayout = left
    private val mRightLayout: ConstraintLayout = right

    fun createTextView(parentView: ConstraintLayout) {
        val aaa = EditText(parentView.context)
        parentView.removeAllViews()
        val textView = TextView(parentView.context)
        textView.id = View.generateViewId()
        parentView.addView(textView)
        val set = ConstraintSet()
        set.clone(parentView)
        set.match(textView,parentView)
    }
    private fun ConstraintSet.match(view: View, parentView: View) {
        this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
        this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
        this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
        this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
    }
}