package com.cartoonHero.source.redux.reducers

import com.cartoonHero.source.redux.states.ActivityState
import org.rekotlin.Action

fun activityReducer(action: Action, state: ActivityState?): ActivityState {
    val actState = state?: ActivityState()
    return actState.copy(currentAction = action)
}