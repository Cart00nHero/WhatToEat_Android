package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cartoonhero.source.actors.dataManger.TabMenuTemplate
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

    init {
        inflate(context, R.layout.layout_tab_menu, this)
    }
    open fun initializeLayout() {
        removeAllFragments()
        this.tabViewPager.adapter = attachedActivity?.let { VP2FragmentStateAdapter(it) }
        TabLayoutMediator(this.tabOptLayout, this.tabViewPager) { tab, position ->
            tab.text = template?.tabItems?.get(position)
        }.attach()
    }

    private fun removeAllFragments() {
        if (template?.vpFragments?.isNotEmpty() == true) {
            for (fragment in vpFragments) {
                if (fragment.isAdded && fragment.activity != null) {
                    (fragment.activity as AppCompatActivity).removeFragment(fragment)
                }
            }
        }
        for (fragment in vpFragments) {
            if (fragment.isAdded && fragment.activity != null) {
                (fragment.activity as AppCompatActivity).removeFragment(fragment)
            }
        }
        vpFragments.clear()
        template?.let { vpFragments.addAll(it.vpFragments) }
    }


    private inner class VP2FragmentStateAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return vpFragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return vpFragments[position]
        }

    }
    private inner class VP2FragmentPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(
        fragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getCount(): Int {
            return vpFragments.size
        }

        override fun getItem(position: Int): Fragment {
            return vpFragments[position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE;
        }

    }
}