package com.cartoonhero.source.stage.scenery.customLayouts

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.cartoonhero.source.stage.scenery.templates.TabMenuLayout
import kotlinx.android.synthetic.main.layout_tab_menu.view.*

class CustomTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TabMenuLayout(context, attrs, defStyleAttr) {

    override fun initializeLayout() {
        super.initializeLayout()
        initViewPager()
        if (template.viewItem.tabItems.size > 0) {
            TabLayoutMediator(this.menuTabLayout, this.tabViewPager) { tab, position ->
                // faced strange out of index here
                val tvItem = template.viewItem.tabItems[position]
                tab.text = tvItem.text
                this.menuTabLayout.setTabTextColors(tvItem.textColor,tvItem.selectColor)
            }.attach()
        }
        this.tabViewPager.setCurrentItem(
            template.viewItem.selectedIndex,false)
        this.menuTabLayout.setScrollPosition(
            template.viewItem.selectedIndex,0F,true)
        setTabPosition(template.viewItem.selectedIndex)
    }
    private fun setTabPosition(position: Int) {
        if (position < template.viewItem.tabItems.size) {
            template.viewItem.selectedIndex = position
            val selectedTab = this.menuTabLayout.getTabAt(template.viewItem.selectedIndex)
            selectedTab?.select()
        }
    }

    private fun initViewPager() {
        this.tabViewPager.adapter = null
        this.tabViewPager.adapter = VP2FragmentStateAdapter(attachedActivity)
        this.tabViewPager.orientation =
            template.viewItem.vpItem.vpOrientation
        this.tabViewPager.isUserInputEnabled =
            template.viewItem.vpItem.isUserInputEnabled
    }

    private inner class VP2FragmentStateAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return getFragments().size
        }
        /* Here will happen FragmentManager is already executing transactions
        override fun containsItem(itemId: Long): Boolean {
            return createdIds.contains(itemId)
        }
        override fun getItemId(position: Int): Long {
            return fragmentIds[position]
        }
        */
        override fun createFragment(position: Int): Fragment {
            sortCreatedIds(position)
            return getFragments()[position]
        }
    }
}