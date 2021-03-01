package com.cartoonHero.source.stage.scenery.templates

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.cartoonHero.source.enities.TabMenuTemplate
import com.cartoonHero.source.whatToEat.R

open class TabMenuLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var attachedActivity: AppCompatActivity
    lateinit var template: TabMenuTemplate
    private val vpFragments = mutableListOf<Fragment>()
    private val fragmentIds = mutableListOf<Long>()
    private val createdIds = hashSetOf<Long>()

    init {
        inflate(context, R.layout.layout_tab_menu, this)
    }

    open fun initializeLayout() {
        cleanData()
        vpFragments.addAll(template.viewItem.vpItem.vpFragments)
    }
    fun getFragments(): List<Fragment> {
        return vpFragments
    }
    fun fragmentId(position: Int): Long {
        return fragmentIds[position]
    }
    fun sortCreatedIds(position: Int) {
        createdIds.add(fragmentIds[position])
    }
    fun isContainsId(itemId: Long): Boolean {
        return createdIds.contains(itemId)
    }

    private fun cleanData() {
        fragmentIds.clear()
        createdIds.clear()
        for (fragment in template.viewItem.vpItem.vpFragments) {
            val newId = View.generateViewId()
            fragmentIds.add(newId.toLong())
        }
        vpFragments.clear()
    }
}