package com.cartoonHero.source.stage.scene.addGourmets.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonHero.source.agent.ActivityStateListener
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.addGourmets.scenarios.AddGourmetScenario
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.makeBounceEffect
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.fragment_add_gourmet.*
import kotlinx.coroutines.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class AddGourmetFragment: Fragment() {
    private val scenario: AddGourmetScenario = AddGourmetScenario()

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
        return inflater.inflate(R.layout.fragment_add_gourmet, container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scenario.toBeGetInputData {
            CoroutineScope(Dispatchers.Main).launch {
                val rvAdapter = RecyclerAdapter()
                agRecyclerView.adapter = rvAdapter
                agRecyclerView.edgeEffectFactory = makeBounceEffect(requireContext())
            }
        }
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

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
        }
    }

    private inner class RecyclerAdapter: BounceRecyclerAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BounceViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: BounceViewHolder, position: Int) {
            addViewHolderListener(vhListener)
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        override fun onMoveItem(from: Int, to: Int) {
            TODO("Not yet implemented")
        }

        override fun onRemoveItem(from: Int) {
            TODO("Not yet implemented")
        }
        private val vhListener = object : ViewHolderListener {
            override fun onHolderBound(itemView: View, position: Int) {
                TODO("Not yet implemented")
            }

        }
    }
}