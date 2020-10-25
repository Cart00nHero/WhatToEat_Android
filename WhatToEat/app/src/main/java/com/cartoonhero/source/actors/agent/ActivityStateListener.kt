package com.cartoonhero.source.actors.agent

import com.cartoonhero.source.redux.states.ActivityState

interface ActivityStateListener {
    fun onNewState(state: ActivityState)
}