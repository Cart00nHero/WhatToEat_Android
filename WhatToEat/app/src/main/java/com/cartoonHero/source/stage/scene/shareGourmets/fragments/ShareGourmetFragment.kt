package com.cartoonHero.source.stage.scene.shareGourmets.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.cartoonHero.source.agent.ActivityStateListener
import com.cartoonHero.source.props.*
import com.cartoonHero.source.props.enities.*
import com.cartoonHero.source.redux.actions.ItemEditTextDidChangedAction
import com.cartoonHero.source.redux.actions.ViewOnClickAction
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.shareGourmets.scenarios.ShareGourmetScenario
import com.cartoonHero.source.stage.scenery.listItems.ShareGourmetLRItem
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.fragment_share_gourmet.*
import kotlinx.coroutines.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class ShareGourmetFragment : Fragment() {
    private val scenario: ShareGourmetScenario = ShareGourmetScenario()
    private val listData: GourmetsListData = GourmetsListData(initGQInputObject())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scenario.toBeCollectGQInputParcel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        scenario.toBeCollectGQInputParcel()
        return inflater.inflate(
            R.layout.fragment_share_gourmet, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scenario.toBeGetInputData {
            listData.reloadData(it)
            CoroutineScope(Dispatchers.Main).launch {
                val concatAdapter = ConcatAdapter()
                for (i in 0 until listData.dataSource.size) {
                    concatAdapter.addAdapter(
                        RecyclerAdapter(i,listData.dataSource[i]))
                }
                share_recyclerView.adapter = concatAdapter
            }
        }
    }

    override fun onStart() {
        super.onStart()
        share_recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).addStateListener(stateChangedListener)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).removeStateListener(stateChangedListener)
    }

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
            when(state.currentAction) {
                is ItemEditTextDidChangedAction -> {
                    val action =
                        state.currentAction as ItemEditTextDidChangedAction
                    if (action.itemView is ShareGourmetLRItem) {
                        val posPath = action.itemView.concatPosition()
                        val data =
                            listData.dataSource[posPath.section][posPath.position]
                        when(data.templateStyle) {
                            TemplateStyle.LeftRight -> {
                                scenario.toBeUpdateInputData(action.text,posPath)
                                val item =
                                    (data as LRTemplate).rightViewItem as EditTextItem
                                item.text = action.text
                            }
                            else -> {}
                        }
                    }
                }
                is ViewOnClickAction -> {
                    scenario.toBeGetInputData {
                        CoroutineScope(Dispatchers.Main).launch {
                            Log.d("Test title",it.shopBranch.title)
                            Log.d("Test subtitle",it.shopBranch.subtitle.value ?: "")
                        }
                    }
                }
            }
        }
    }

    private inner class RecyclerAdapter(private val section: Int,
        private val source: List<TemplateInterface>) : BounceRecyclerAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BounceViewHolder {
            val data = source[viewType]
            val itemView = when (data.templateStyle) {
                TemplateStyle.LeftRight -> {
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_share_gourmet_lr_item, parent, false)
                }
                else -> LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_lr_item, parent, false)
            }
            return BounceViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: BounceViewHolder, position: Int) {
            onHolderBound(holder.itemView, position)
        }

        override fun getItemCount(): Int {
            return source.size
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        private fun onHolderBound(itemView: View, position: Int) {
            val data = source[position]
            var itemHeight = 0
            activity?.screenSizeInDp?.apply {
                itemHeight = x * data.itemHeight.toDp(context!!)/375
            }
            if (itemView is ShareGourmetLRItem) {
                itemView.setConcatPosition(section,position)
                itemView.layoutParams.height = itemHeight
                itemView.template = data as LRTemplate
                itemView.initializeLayout()
            }
        }
    }
}