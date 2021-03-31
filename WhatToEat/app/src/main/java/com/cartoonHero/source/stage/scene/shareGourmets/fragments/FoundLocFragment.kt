package com.cartoonHero.source.stage.scene.shareGourmets.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.cartoonHero.source.props.enities.GQInputObject
import com.cartoonHero.source.props.enities.ImageViewItem
import com.cartoonHero.source.props.enities.TextViewItem
import com.cartoonHero.source.props.enities.UDTemplate
import com.cartoonHero.source.props.screenSizeInDp
import com.cartoonHero.source.props.toDp
import com.cartoonHero.source.stage.scene.shareGourmets.scenarios.FoundLocScenario
import com.cartoonHero.source.stage.scenery.listItems.GridUDItemView
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.fragment_found_location.*
import kotlinx.coroutines.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class FoundLocFragment: Fragment() {

    private val scenario = FoundLocScenario()
    private var dataSource = mutableListOf<GQInputObject>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_found_location,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (foundLoc_recyclerView.layoutManager
                as GridLayoutManager).spanCount = 3
        foundLoc_recyclerView.adapter = RecyclerAdapter()
//        scenario.toBeCollectParcel {
//            dataSource.clear()
//            dataSource.addAll(it)
//            CoroutineScope(Dispatchers.Main).launch {
//            }
//        }
    }

    private inner class RecyclerAdapter: BounceRecyclerAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BounceViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_grid_ud_item, parent, false)
            return BounceViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: BounceViewHolder, position: Int) {
            (holder.itemView as GridUDItemView).setBackgroundColor(
                Color.parseColor("#50FFF8DC"))
            onHolderBound(holder.itemView,position)
        }

        override fun getItemCount(): Int {
            return 6
        }

        private fun onHolderBound(itemView: GridUDItemView, position: Int) {
            itemView.tag = position
            val width = foundLoc_recyclerView.measuredWidth/3
            itemView.layoutParams.height = width + 30.toDp(context!!)
            itemView.template = UDTemplate(
                bottomHeight = 30,
                upViewItem = ImageViewItem(
                    imageDrawable = R.drawable.icon_shop,
                    scaleType = ImageView.ScaleType.FIT_CENTER
                ),
                downViewItem = TextViewItem(
                    text = "WTF",
                    textColor = Color.parseColor("#704214"),
                    textSize = 14.0F
                )
            )
            itemView.initializeLayout()
        }
    }
}