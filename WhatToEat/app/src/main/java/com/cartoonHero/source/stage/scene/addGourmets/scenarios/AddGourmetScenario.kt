package com.cartoonHero.source.stage.scene.addGourmets.scenarios

import com.cartoonHero.source.actorModel.Actor
import com.cartoonHero.source.actors.express.LogisticsCenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AddGourmetScenario: Actor() {
    fun sendTestMessage() {
        send {
            beTestMessage()
        }
    }

    private fun beTestMessage() {
        LogisticsCenter.applyExpressService(this,"","")
    }
}