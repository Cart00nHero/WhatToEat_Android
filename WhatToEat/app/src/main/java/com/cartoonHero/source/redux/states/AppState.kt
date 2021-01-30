package com.cartoonHero.source.redux.states

import org.rekotlin.StateType

data class AppState (
    val activityState: ActivityState?
): StateType