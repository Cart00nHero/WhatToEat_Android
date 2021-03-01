package com.cartoonHero.source.actorModel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.collect
import com.cartoonHero.source.actorModel.AppStream.messages

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
abstract class Actor {

    private val scope = CoroutineScope(Dispatchers.Default + Job())

    fun start() = scope.launch {
        val actor = actor<Message>(scope.coroutineContext) {
            for (msg in channel) {
                act(msg)
            }
        }
        messages.collect(actor::send)
    }

    fun stop() {
        scope.cancel()
    }

    protected fun send(message: Message) = AppStream.send(message)

    protected open suspend fun act(message: Message) {}
}