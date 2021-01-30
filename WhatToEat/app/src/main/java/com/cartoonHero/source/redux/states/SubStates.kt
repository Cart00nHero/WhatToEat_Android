package com.cartoonHero.source.redux.states

import org.rekotlin.Action
import org.rekotlin.StateType

data class ActivityState (
    var currentAction: Action? = null
): StateType