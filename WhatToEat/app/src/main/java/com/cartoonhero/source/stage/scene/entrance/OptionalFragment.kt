package com.cartoonhero.source.stage.scene.entrance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.agent.ActivityStateDelegate
import com.cartoonhero.source.redux.states.ActivityState
import com.cartoonhero.source.whattoeat.MainActivity
import com.cartoonhero.source.whattoeat.R

class OptionalFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_optional,container,false)
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setOnStateChangedDelegate(stateChangedListener)
    }
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).removeDelegate()
    }

    private val stateChangedListener = object : ActivityStateDelegate {
        override fun receiveNewState(state: ActivityState) {
        }
    }

}