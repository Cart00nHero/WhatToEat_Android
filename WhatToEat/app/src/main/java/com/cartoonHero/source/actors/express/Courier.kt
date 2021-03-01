package com.cartoonHero.source.actors.express

import com.cartoonHero.source.actorModel.Actor
import com.cartoonHero.source.enities.Parcel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class Courier : Actor() {
    private val courierBag: MutableMap<String, HashSet<Parcel>> = mutableMapOf()
    fun sendClaimParcel(recipient: String, parcel: Parcel) {
        send {
            beClaimParcel(recipient,parcel)
        }
    }
    fun sendCollect(
        recipient: Actor,complete: (HashSet<Parcel>) -> Unit) {
        send {
            beCollect(recipient,complete)
        }
    }
    fun sendCancel(recipient:String,parcel: Parcel) {
        send {
            beCancel(recipient,parcel)
        }
    }
    fun sendCleanBag() {
        send {
            beCleanBag()
        }
    }

    private fun beClaimParcel(recipient: String, parcel: Parcel) {
        val parcelSet: HashSet<Parcel> =
            if (courierBag[recipient] == null) {
                hashSetOf()
            } else {
                courierBag[recipient]!!
            }
        if (!parcelSet.contains(parcel)) {
            parcelSet.add(parcel)
            courierBag[recipient] = parcelSet
        }
    }
    private fun beCollect(
        recipient: Actor,complete: (HashSet<Parcel>) -> Unit) {
        val key = recipient.javaClass.name
        val parcelSet = courierBag[key]?.toHashSet()
        courierBag.remove(key)
        recipient.send {
            parcelSet?.let { complete(it) }
        }
    }
    private fun beCancel(recipient:String, parcel: Parcel) {
        val parcelSet = courierBag[recipient]?.toHashSet()
        if (parcelSet?.contains(parcel) == true){
            parcelSet.remove(parcel)
            if (parcelSet.count() == 0) {
                courierBag.remove(recipient)
            } else {
                courierBag[recipient] = parcelSet
            }
        }
    }
    private fun beCleanBag() {
        courierBag.clear()
    }
}