package com.cartoonhero.source.redux.reducers

import com.cartoonhero.source.redux.states.AppState
import org.rekotlin.Action

fun appReducer(action: Action, state: AppState?): AppState {
    return AppState(
        activityState = activityReducer(action, state?.activityState)
    )
}