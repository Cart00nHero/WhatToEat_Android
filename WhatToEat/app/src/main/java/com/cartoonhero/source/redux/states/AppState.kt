package com.cartoonhero.source.redux.states

import org.rekotlin.StateType

data class AppState (
    val activityState: ActivityState?
): StateType