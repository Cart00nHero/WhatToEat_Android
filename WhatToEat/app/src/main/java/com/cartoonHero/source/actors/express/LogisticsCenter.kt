package com.cartoonHero.source.actors.express

import com.cartoonHero.source.actorModel.Actor
import com.cartoonHero.source.enities.Parcel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
object LogisticsCenter {
    val courier = Courier()
    inline fun <reified T> applyExpressService(
        sender: Actor, recipient: String, content: T): Parcel {
        val typeName = T::class.java.name
        val senderName = sender.javaClass.name
        val parcel = Parcel(typeName,senderName, content!!)
        courier.sendBeClaimParcel(recipient,parcel)
        return parcel
    }
}