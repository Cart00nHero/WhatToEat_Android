package com.cartoonHero.source.actors.express

import com.cartoonHero.source.actorModel.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

data class Parcel(
    val contentType: String,
    var sender: String = "",
    var content: Any
)
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
object LogisticsCenter {
    val courier = Courier()
    init {
        courier.start()
    }
    inline fun <reified T> applyExpressService(
        sender: Actor, recipient: String, content: T): Parcel {
        val typeName = T::class.java.name
        val senderName = sender.javaClass.name
        val parcel = Parcel(typeName,senderName, content!!)
        courier.sendBeClaimParcel(recipient,parcel)
        return parcel
    }
}