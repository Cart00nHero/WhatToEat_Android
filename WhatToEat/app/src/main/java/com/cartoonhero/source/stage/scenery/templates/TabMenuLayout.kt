package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cartoonhero.source.actors.datamanger.TabMenuTemplate
import com.cartoonhero.source.actors.toolMan.inlineCls.removeFragment
import com.cartoonhero.source.whattoeat.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.layout_tab_menu.view.*

open class TabMenuLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var attachedActivity: AppCompatActivity
    lateinit var template: TabMenuTemplate
    private val vpFragments = mutableListOf<Fragment>()

    init {
        inflate(context, R.layout.layout_tab_menu, this)
    }
    open fun initializeLayout() {
        removeAllFragments()
        this.tabViewPager.adapter = attachedActivity.let { VP2FragmentStateAdapter(it) }
        TabLayoutMediator(this.tabOptLayout, this.tabViewPager) { tab, position ->
            val tvItem = template.contentItem.tabItems[position]
            tab.text = tvItem.text
            this.tabOptLayout.setTabTextColors(tvItem.textColor,tvItem.selectColor)
        }.attach()
    }

    private fun removeAllFragments() {
        if (template.contentItem.vpItem.vpFragments.size > 0) {
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
        vpFragments.addAll(template.contentItem.vpItem.vpFragments)
    }


    private inner class VP2FragmentStateAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return vpFragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return vpFragments[position]
        }

    }
}