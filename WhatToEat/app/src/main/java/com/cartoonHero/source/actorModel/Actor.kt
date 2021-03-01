package com.cartoonHero.source.actorModel

import com.cartoonHero.source.actorModel.AppStream.messages
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.collect

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
abstract class Actor {

    private val scope = CoroutineScope(Dispatchers.Default + Job())
    protected lateinit var actorSendBack: () -> Unit

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
    fun sendBack(sendBack: () -> Unit) {
        actorSendBack = sendBack
        send(SendBackMessage(CompletableDeferred()))
    }
    protected open suspend fun act(message: Message) {}
    protected data class SendBackMessage(
        val response: CompletableDeferred<Unit>):Message
}