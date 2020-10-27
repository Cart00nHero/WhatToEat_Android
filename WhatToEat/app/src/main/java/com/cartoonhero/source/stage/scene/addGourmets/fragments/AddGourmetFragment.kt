package com.cartoonhero.source.stage.scene.addGourmets.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.agent.ActivityStateListener
import com.cartoonhero.source.actors.inlineCls.screenSizeInDp
import com.cartoonhero.source.redux.states.ActivityState
import com.cartoonhero.source.stage.scene.addGourmets.presenters.AddGourmetPresenter
import com.cartoonhero.source.stage.scenery.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonhero.source.stage.scenery.bounceRecyclerView.makeBounceEffect
import com.cartoonhero.source.stage.scenery.itemTemplates.LRItemFactory
import com.cartoonhero.source.whattoeat.MainActivity
import com.cartoonhero.source.whattoeat.R
import kotlinx.android.synthetic.main.fragment_add_gourmet.*
import kotlinx.android.synthetic.main.rvitem_lr_itemview.view.*

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
        agRecyclerView.adapter = RecyclerAdapter()
        agRecyclerView.edgeEffectFactory = makeBounceEffect(requireContext())
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).addStateListener(stateChangedListener)
    }
    inner class RecyclerAdapter: BounceRecyclerAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            addViewHolderListener(vhListener)
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.rvitem_lr_itemview, parent, false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            super.onBindViewHolder(holder, position)
            holder.itemView.tag = position
            activity?.screenSizeInDp?.apply {
                holder.itemView.layoutParams.height = x * 100/375
            }
        }
        private val vhListener = object : ViewHolderListener {
            override fun onBindViewHolder(itemView: View) {
            }
        }
    }

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
        }
    }
}