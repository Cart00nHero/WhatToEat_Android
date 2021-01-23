package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.dataManger.FragmentVPTemplate
import com.cartoonhero.source.whattoeat.R

open class ViewPagerLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var attachedActivity: AppCompatActivity
    lateinit var template: FragmentVPTemplate
    private val vpFragments = mutableListOf<Fragment>()
    private val fragmentIds = mutableListOf<Long>()
    private val createdIds = hashSetOf<Long>()

    init {
        inflate(context, R.layout.layout_viewpager, this)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    open fun initializeLayout() {
        // clean previous state first
        cleanData()
        vpFragments.addAll(template.viewPagerItem.vpFragments)
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
        for (fragment in template.viewPagerItem.vpFragments) {
            val newId = View.generateViewId()
            fragmentIds.add(newId.toLong())
        }
        vpFragments.clear()
    }
}