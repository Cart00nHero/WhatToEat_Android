package com.cartoonHero.source.stage.scenery.customLayouts

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cartoonHero.source.inlineMethods.toDp
import com.cartoonHero.source.stage.scenery.templates.ViewPagerLayout
import kotlinx.android.synthetic.main.layout_viewpager.view.*

class CustomViewPager @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewPagerLayout(context, attrs, defStyleAttr) {
    override fun initializeLayout() {
        super.initializeLayout()
        this.tmpViewPager.adapter = null
        this.tmpViewPager.isUserInputEnabled = template.vpItem.isUserInputEnabled
        this.tmpViewPager.orientation = template.vpItem.vpOrientation
        this.tmpViewPager.adapter = VP2FragmentStateAdapter(attachedActivity)
        this.tmpViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (template.itemHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    val fragment = getFragments()[position]
                    fragment.view?.post {
                        val wMeasureSpec = MeasureSpec.makeMeasureSpec(fragment.requireView().width, MeasureSpec.EXACTLY)
                        val hMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                        fragment.requireView().measure(wMeasureSpec, hMeasureSpec)
                        if (tmpViewPager.layoutParams.height != fragment.requireView().measuredHeight) {
                            // ParentViewGroup is, for example, LinearLayout
                            // ... or whatever the parent of the ViewPager2 is
                            (tmpViewPager.layoutParams).also {
                                // 這個公式是湊出來的，但好像可以用 XD
                                template.itemHeight = (fragment.requireView().measuredHeight/2)+20.toDp(context)
                            }
                        }
                    }
                }
            }
        })
    }

    private inner class VP2FragmentStateAdapter(activity: AppCompatActivity):
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return getFragments().size
        }
        override fun containsItem(itemId: Long): Boolean {
            return isContainsId(itemId)
        }

        override fun getItemId(position: Int): Long {
            return fragmentId(position)
        }
        override fun createFragment(position: Int): Fragment {
            sortCreatedIds(position)
            return getFragments()[position]
        }
    }
}