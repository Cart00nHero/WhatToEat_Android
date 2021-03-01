package com.cartoonHero.source.agent

import com.cartoonHero.source.redux.states.ActivityState

interface ActivityStateListener {
    fun onNewState(state: ActivityState)
}