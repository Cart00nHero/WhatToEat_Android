package com.cartoonHero.source.actors

import com.cartoonHero.source.actors.model.AppStream
import com.cartoonHero.source.actors.model.AppStream.messages
import com.cartoonHero.source.actors.model.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.collect

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi

abstract class Actor {

    private val scope = CoroutineScope(Dispatchers.Default + Job())

    fun start() = scope.launch {
        val actor = actor<Message>(scope.coroutineContext) {
            for (msg in channel) {
                receive(msg)
            }
        }
        messages.collect(actor::send)
    }

    fun stop() {
        scope.cancel()
    }

    protected fun send(message: Message) = AppStream.send(message)
    protected open suspend fun receive(message: Message) {}
}