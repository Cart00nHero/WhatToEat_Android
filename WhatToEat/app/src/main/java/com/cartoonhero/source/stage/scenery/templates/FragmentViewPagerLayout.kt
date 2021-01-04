package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cartoonhero.source.actors.dataManger.FragmentVPTemplate
import com.cartoonhero.source.whattoeat.R
import kotlinx.android.synthetic.main.layout_fragment_viewpager.view.*

open class FragmentViewPagerLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var attachedActivity: AppCompatActivity
    lateinit var template: FragmentVPTemplate
    private val vpFragments = mutableListOf<Fragment>()
    private val fragmentIds = mutableListOf<Long>()
    private val createdIds = hashSetOf<Long>()

    init {
        inflate(context, R.layout.layout_fragment_viewpager, this)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    open fun initializeLayout() {
        // clean previous state first
        cleanItemData()
        vpFragments.addAll(template.vpItem.vpFragments)
        this.fragmentViewPager.adapter = null
        this.fragmentViewPager.isUserInputEnabled = template.vpItem.isUserInputEnabled
        this.fragmentViewPager.orientation = template.vpItem.vpOrientation
        this.fragmentViewPager.adapter = VP2FragmentStateAdapter(attachedActivity)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun cleanItemData() {
        fragmentIds.clear()
        createdIds.clear()
        for (fragment in template.vpItem.vpFragments) {
            val newId = View.generateViewId()
            fragmentIds.add(newId.toLong())
        }
        vpFragments.clear()
    }
    private inner class VP2FragmentStateAdapter(activity: AppCompatActivity):
        FragmentStateAdapter(activity) {
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