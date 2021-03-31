package com.cartoonHero.source.stage.scene.entrance.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonHero.source.agent.ActivityStateListener
import com.cartoonHero.source.redux.actions.NetWorkStatus
import com.cartoonHero.source.redux.actions.SceneGoForwardAction
import com.cartoonHero.source.redux.actions.SignFoodieAction
import com.cartoonHero.source.redux.appStore
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.entrance.fragments.OptionalFragment
import com.cartoonHero.source.stage.scene.shareGourmets.scenarios.ShareGourmetScenario
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SignFragment: Fragment() {
    private val sss = ShareGourmetScenario()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        fb_sign_button.setOnClickListener { _ ->
//        }
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
            when(state.currentAction) {
                is SignFoodieAction -> {
                    val action = state.currentAction as SignFoodieAction
                    when(action.status) {
                        NetWorkStatus.SUCCESS -> {
                            appStore.dispatch(SceneGoForwardAction(listOf(OptionalFragment())))
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}