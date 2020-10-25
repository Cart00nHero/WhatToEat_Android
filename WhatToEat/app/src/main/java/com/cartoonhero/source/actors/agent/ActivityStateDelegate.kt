package com.cartoonhero.source.actors.agent

import com.cartoonhero.source.redux.states.ActivityState

interface ActivityStateDelegate {
    fun receiveNewState(state: ActivityState)
}