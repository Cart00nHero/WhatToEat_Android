package com.mitake.base.amSrc.stage.scenery.templates

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cartoonhero.source.actors.dataManger.TabMenuTemplate
import com.cartoonhero.source.whattoeat.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.layout_tab_menu.view.*

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            cleanItemData()
        }
        vpFragments.addAll(template.contentItem.vpItem.vpFragments)
        initViewPager()
        if (template.contentItem.tabItems.size > 0) {
            TabLayoutMediator(this.tabOptLayout, this.tabViewPager) { tab, position ->
                // faced strange out of index here
                val tvItem = template.contentItem.tabItems[position]
                tab.text = tvItem.text
                this.tabOptLayout.setTabTextColors(tvItem.textColor,tvItem.selectColor)
            }.attach()
        }
        this.tabViewPager.setCurrentItem(
                template.contentItem.selectedIndex,false)
        this.tabOptLayout.setScrollPosition(
                template.contentItem.selectedIndex,0F,true)
        setTabPosition(template.contentItem.selectedIndex)
    }

    fun setTabPosition(position: Int) {
        if (position < template.contentItem.tabItems.size) {
            template.contentItem.selectedIndex = position
            val selectedTab = this.tabOptLayout.getTabAt(template.contentItem.selectedIndex)
            selectedTab?.select()
        }
    }

    private fun initViewPager() {
        this.tabViewPager.adapter = null
        this.tabViewPager.adapter = VP2FragmentStateAdapter(attachedActivity)
        this.tabViewPager.orientation =
                template.contentItem.vpItem.vpOrientation
        this.tabViewPager.isUserInputEnabled =
                template.contentItem.vpItem.isUserInputEnabled
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun cleanItemData() {
        fragmentIds.clear()
        createdIds.clear()
        for (fragment in template.contentItem.vpItem.vpFragments) {
            val newId = View.generateViewId()
            fragmentIds.add(newId.toLong())
        }
        vpFragments.clear()
    }


    private inner class VP2FragmentStateAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return vpFragments.size
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
            createdIds.add(fragmentIds[position])
            return vpFragments[position]
        }
    }
}