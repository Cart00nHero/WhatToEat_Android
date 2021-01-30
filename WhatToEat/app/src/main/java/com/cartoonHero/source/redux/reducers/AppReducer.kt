package com.cartoonHero.source.redux.reducers

import com.cartoonHero.source.redux.states.AppState
import org.rekotlin.Action

fun appReducer(action: Action, state: AppState?): AppState {
    return AppState(
        activityState = activityReducer(action, state?.activityState)
    )
}