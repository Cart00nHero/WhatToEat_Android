package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cartoonhero.source.actors.dataManger.FragmentVPTemplate
import com.cartoonhero.source.actors.toolMan.inlineCls.toDp
import com.cartoonhero.source.redux.appStore
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
        this.fragmentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (template.itemHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    val fragment = vpFragments[position]
                    fragment.view?.post {
                        val wMeasureSpec = MeasureSpec.makeMeasureSpec(fragment.requireView().width, MeasureSpec.EXACTLY)
                        val hMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                        fragment.requireView().measure(wMeasureSpec, hMeasureSpec)

                        if (fragmentViewPager.layoutParams.height != fragment.requireView().measuredHeight) {
                            // ParentViewGroup is, for example, LinearLayout
                            // ... or whatever the parent of the ViewPager2 is
                            (fragmentViewPager.layoutParams).also {
                                // 這個公式是湊出來的，但好像可以用 XD
                                template.itemHeight = (fragment.requireView().measuredHeight/2)+20.toDp(context)
                            }
                        }
                    }
                }
            }
        })
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