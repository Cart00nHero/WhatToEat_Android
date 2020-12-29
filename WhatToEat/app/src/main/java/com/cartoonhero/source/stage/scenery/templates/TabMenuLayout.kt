package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cartoonhero.source.actors.dataManger.TabMenuTemplate
import com.cartoonhero.source.actors.toolMan.inlineCls.findFragment
import com.cartoonhero.source.actors.toolMan.inlineCls.removeFragment
import com.cartoonhero.source.whattoeat.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.layout_tab_menu.view.*

open class TabMenuLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var template: TabMenuTemplate? = null
    var attachedActivity: AppCompatActivity? = null
    private val vpFragments = mutableListOf<Fragment>()
    private val fragmentIds = mutableListOf<Long>() //用于存储更新fragment的特定标识
    private val createdIds = hashSetOf<Long>() // //得用HashSet防重，用于存储adapter内的顺序

    init {
        inflate(context, R.layout.layout_tab_menu, this)
    }
    open fun initializeLayout() {
        cleanFragments()
        this.tabViewPager.adapter = attachedActivity?.let { VP2FragmentStateAdapter(it) }
        TabLayoutMediator(this.tabOptLayout, this.tabViewPager) { tab, position ->
            tab.text = template?.tabItems?.get(position)
        }.attach()
    }

    private fun cleanFragments() {
        fragmentIds.clear()
        if (template?.vpFragments?.isNotEmpty() == true) {
            for (fragment in template?.vpFragments!!) {
                val fragment = attachedActivity?.findFragment(fragment.id)
                if (fragment != null && fragment.isAdded) {
                    attachedActivity?.removeFragment(fragment)
                }
                val newId = View.generateViewId()
                fragmentIds.add(newId.toLong())
            }
        }

        vpFragments.clear()
        template?.let { vpFragments.addAll(it.vpFragments) }
    }


    private inner class VP2FragmentStateAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return vpFragments.size
        }

        override fun containsItem(itemId: Long): Boolean {
            return createdIds.contains(itemId)
        }

        override fun getItemId(position: Int): Long {
            return fragmentIds[position]
        }
        override fun createFragment(position: Int): Fragment {
            createdIds.add(fragmentIds[position])
            return vpFragments[position]
        }

    }
}