package com.cartoonHero.source.stage.scene.entrance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonHero.source.agent.ActivityStateListener
import com.cartoonHero.source.dslMethods.convertModelToJson
import com.cartoonHero.source.redux.actions.NetWorkStatus
import com.cartoonHero.source.redux.actions.SceneGoForwardAction
import com.cartoonHero.source.redux.actions.SignFoodieAction
import com.cartoonHero.source.redux.appStore
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.addGourmets.scenarios.AddGourmetScenario
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.fragment_sign.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import type.InputShop

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SignFragment: Fragment() {
    private val sss = AddGourmetScenario()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bbb = InputShop(
            title = "",
            underPrice = 0.0
        )
        Log.d("AAAAAa",convertModelToJson(bbb))
        fb_sign_button.setOnClickListener { _ ->
            sss.sendTestMessage()
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