package com.cartoonHero.source.stage.scene.entrance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonHero.source.actors.agent.ActivityStateListener
import com.cartoonHero.source.actors.toolMan.inlineCls.startActivity
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.MapsActivity
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.fragment_optional.*

class OptionalFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_optional,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.findButton.setOnClickListener {
            activity?.startActivity<MapsActivity> {
            }
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

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
        }
    }

}