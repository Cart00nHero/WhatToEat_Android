package com.cartoonHero.source.actorModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

interface Message

@ExperimentalCoroutinesApi
class ActorStream {
    private val appScope: CoroutineScope =
        CoroutineScope(EmptyCoroutineContext + SupervisorJob())
    private val messageChannel: BroadcastChannel<Message> = BroadcastChannel(100)

    fun send(event: Message) {
        appScope.launch {
            messageChannel.send(event)
        }
    }

    val messages: Flow<Message>
        get() = flow { emitAll(messageChannel.openSubscription()) }
}