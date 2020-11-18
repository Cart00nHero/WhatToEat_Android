package com.cartoonhero.source.stage.scenery.customLayouts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cartoonhero.source.whattoeat.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.layout_tab_view.view.*

class TabViewLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val tabOptItems: MutableList<String> = mutableListOf()

    init {
        inflate(context, R.layout.layout_tab_view, this)
    }

    fun initLayout(tabLayoutItems:List<String>) {

        tabOptItems.addAll(tabLayoutItems)

        TabLayoutMediator(this.tabOptLayout,this.tabViewPager) { tab, position ->
            tab.text = tabOptItems[position]
        }
        this.tabOptLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })
        this.tabViewPager.adapter = ViewPager2Adapter()
    }

    private inner class ViewPager2Adapter:
            RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_tab_vpitem, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.tag = position
        }

        override fun getItemCount(): Int {
            return tabOptItems.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}