package com.cartoonhero.source.stage.scene.search_locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.agent.ActivityStateListener
import com.cartoonhero.source.redux.states.ActivityState
import com.cartoonhero.source.whattoeat.MainActivity
import com.cartoonhero.source.whattoeat.R

class SearchLocFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_searchloc,container,false)
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
}