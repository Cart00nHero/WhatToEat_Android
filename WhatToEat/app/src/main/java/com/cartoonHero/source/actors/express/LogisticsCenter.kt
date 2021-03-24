package com.cartoonHero.source.actors.express

import com.cartoonHero.source.props.enities.Parcel
import com.cartoonhero.source.actormodel.Actor
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
        courier.toBeClaimParcel(recipient,parcel)
        return parcel
    }

    fun cancelService(recipient:String, parcel: Parcel) {
        courier.toBeCancel(recipient,parcel)
    }
    fun collectParcels(
        recipient: Actor,complete: (HashSet<Parcel>) -> Unit) {
        courier.toBeCollect(recipient, complete)
    }
}