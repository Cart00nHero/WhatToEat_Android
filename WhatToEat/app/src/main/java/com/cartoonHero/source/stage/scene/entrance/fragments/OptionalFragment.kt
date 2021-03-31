package com.cartoonHero.source.stage.scene.entrance.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cartoonHero.source.agent.ActivityStateListener
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.findMyFood.fragments.FindFoodFragment
import com.cartoonHero.source.stage.scene.shareGourmets.fragments.SearchLocationFragment
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.fragment_optional.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class OptionalFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_optional,container,false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
        this.opt_find_button.setOnClickListener {
            (activity as MainActivity).goForward(listOf(FindFoodFragment()))
        }
        this.opt_share_button.setOnClickListener {
            (activity as MainActivity).goForward(listOf(SearchLocationFragment()))
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity)
            .addStateListener(stateChangedListener)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity)
            .removeStateListener(stateChangedListener)
    }
    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
        }
    }

}