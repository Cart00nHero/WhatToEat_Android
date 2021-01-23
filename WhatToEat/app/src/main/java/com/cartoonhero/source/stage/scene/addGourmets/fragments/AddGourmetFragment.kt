package com.cartoonhero.source.stage.scene.addGourmets.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.agent.ActivityStateListener
import com.cartoonhero.source.actors.dataManger.FragmentContainerTemplate
import com.cartoonhero.source.actors.dataManger.TemplateStyle
import com.cartoonhero.source.actors.toolMan.inlineCls.screenSizeInDp
import com.cartoonhero.source.redux.states.ActivityState
import com.cartoonhero.source.stage.scene.addGourmets.presenters.AddGourmetPresenter
import com.cartoonhero.source.actors.specialEffectsArtist.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonhero.source.actors.specialEffectsArtist.bounceRecyclerView.makeBounceEffect
import com.cartoonhero.source.stage.scenery.templates.FragmentContainerLayout
import com.cartoonhero.source.whattoeat.MainActivity
import com.cartoonhero.source.whattoeat.R
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
        agRecyclerView.adapter = RecyclerAdapter()
        agRecyclerView.edgeEffectFactory = makeBounceEffect(requireContext())
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).addStateListener(stateChangedListener)
        agRecyclerView.adapter?.notifyDataSetChanged()
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
            activity?.screenSizeInDp?.apply {
                holder.itemView.layoutParams.height = x * 2000/375
            }
        }
        private val vhListener = object : ViewHolderListener {
            override fun onBindViewHolder(itemView: View, position: Int) {
                val data =
                    presenter.listData.dataSource[position]
                when(data.templateStyle) {
                    TemplateStyle.FragmentContainer -> {
                        (itemView as FragmentContainerLayout).template = data as FragmentContainerTemplate
                    }
                    else -> {}
                }
            }
        }
    }

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
        }
    }
}