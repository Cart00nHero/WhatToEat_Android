package com.cartoonHero.source.actors.express

import com.cartoonHero.source.actors.Actor
import com.cartoonHero.source.actors.model.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class Courier: Actor() {
    override suspend fun receive(message: Message) {
        super.receive(message)
    }
}