package com.cartoonHero.source.actorModel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.collect

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
abstract class Actor {

    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private val stream = ActorStream()
    private lateinit var actorSend: () -> Unit
    private lateinit var actorSendBack: () -> Unit

    init {
        start()
    }

    private fun start() = scope.launch {
        val actor = actor<Message>(scope.coroutineContext) {
            for (msg in channel) {
                act(msg)
            }
        }
        stream.messages.collect(actor::send)
    }

    fun cancel() {
        scope.cancel()
    }

    fun send(sender: () -> Unit) {
        actorSend = sender
        sendMessage(SendActorMessage(CompletableDeferred()))
    }
    fun sendBack(sender: () -> Unit) {
        actorSendBack = sender
        sendMessage(SendBackMessage(CompletableDeferred()))
    }

    private fun sendMessage(message: Message) = stream.send(message)
    private fun act(message: Message) {
        when(message) {
            is SendActorMessage -> actorSend()
            is SendBackMessage -> actorSendBack()
        }
    }
}

private data class SendActorMessage(
    val response: CompletableDeferred<Unit>
): Message
private data class SendBackMessage(
    val response: CompletableDeferred<Unit>
): Message