package com.cartoonHero.source.stage.scene.addGourmets.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.cartoonHero.source.agent.ActivityStateListener
import com.cartoonHero.source.enities.LRTemplate
import com.cartoonHero.source.extensionTools.screenSizeInDp
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.addGourmets.presenters.AddGourmetPresenter
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.makeBounceEffect
import com.cartoonHero.source.stage.scenery.specialEffects.rvItemDragDrop.ItemTouchHelperCallback
import com.cartoonHero.source.stage.scenery.listItems.AddGourmetLRItem
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.fragment_add_gourmet.*

class AddGourmetFragment: Fragment() {

    val presenter = AddGourmetPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_gourmet, container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAdapter = RecyclerAdapter()
        agRecyclerView.adapter = rvAdapter
        agRecyclerView.edgeEffectFactory = makeBounceEffect(requireContext())
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(rvAdapter))
        itemTouchHelper.attachToRecyclerView(agRecyclerView)
    }

    override fun onStart() {
        super.onStart()
        agRecyclerView.adapter?.notifyDataSetChanged()
    }
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).addStateListener(stateChangedListener)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).removeStateListener(stateChangedListener)
    }
    private inner class RecyclerAdapter: BounceRecyclerAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            addViewHolderListener(vhListener)
            val itemData = presenter.listData.dataSource[viewType]
            val itemView =
                when(itemData.templateStyle) {
                    else -> LayoutInflater.from(parent.context).inflate(
                        R.layout.view_addgourmet_lr_item, parent, false)
                }
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return presenter.listData.dataSource.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            super.onBindViewHolder(holder, position)
            holder.itemView.tag = position
        }
        private val vhListener = object : ViewHolderListener {
            override fun onBindViewHolder(itemView: View, position: Int) {
                var screenWidth = 0
                activity?.screenSizeInDp?.apply {
                    screenWidth = x
                }
                val data =
                    presenter.listData.dataSource[position]
                when(data.templateStyle) {
                    else -> {
                        itemView.layoutParams.height = screenWidth * 200/375
                        (data as LRTemplate).leftLayoutWidth = screenWidth/2
                        (itemView as AddGourmetLRItem).template = data
                        itemView.initializeLayout()
                    }
                }
            }
        }
    }

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
        }
    }
}