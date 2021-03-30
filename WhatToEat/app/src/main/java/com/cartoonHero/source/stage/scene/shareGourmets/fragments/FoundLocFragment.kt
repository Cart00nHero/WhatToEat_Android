package com.cartoonHero.source.stage.scene.shareGourmets.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonHero.source.props.enities.GQInputObject
import com.cartoonHero.source.props.enities.ImageViewItem
import com.cartoonHero.source.props.enities.TextViewItem
import com.cartoonHero.source.props.enities.UDTemplate
import com.cartoonHero.source.stage.scene.shareGourmets.scenarios.FoundLocScenario
import com.cartoonHero.source.stage.scenery.listItems.GridUDItemView
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonHero.source.whatToEat.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

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
        scenario.toBeCollectParcel {
            dataSource.clear()
            dataSource.addAll(it)
        }
    }

    private inner class RecyclerAdapter(): BounceRecyclerAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BounceViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_grid_ud_item, parent, false)
            return BounceViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: BounceViewHolder, position: Int) {
            onHolderBound(holder.itemView,position)
        }

        override fun getItemCount(): Int {
            return dataSource.size
        }

        private fun onHolderBound(itemView: View, position: Int) {
            (itemView as GridUDItemView).template = UDTemplate(
                upViewItem = ImageViewItem(),
                downViewItem = TextViewItem()
            )
            itemView.initializeLayout()
        }
    }
}