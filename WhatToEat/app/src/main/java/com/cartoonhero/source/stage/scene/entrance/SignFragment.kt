package com.cartoonhero.source.stage.scene.entrance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.agent.ActivityStateDelegate
import com.cartoonhero.source.redux.actions.SignAppAction
import com.cartoonhero.source.redux.appStore
import com.cartoonhero.source.redux.states.ActivityState
import com.cartoonhero.source.whattoeat.MainActivity
import com.cartoonhero.source.whattoeat.R
import kotlinx.android.synthetic.main.fragment_sign.*

class SignFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fbSignButton.setOnClickListener { _ ->
            (activity as MainActivity).goForwardPage(listOf(OptionalFragment()), R.id.contentFrameLayout)
        }
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