package com.cartoonHero.source.actors.express

import com.cartoonHero.source.actorModel.Actor
import com.cartoonHero.source.actorModel.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class Courier : Actor() {
    private val courierBag: MutableMap<String, HashSet<Parcel>> = mutableMapOf()
    fun sendBeClaimParcel(recipient: String, parcel: Parcel) {
        send(BeClaimParcel(recipient,parcel))
    }
    fun sendBeCollect(recipient: Actor) {
        send(BeCollect(recipient))
    }
    fun sendBeCancel(recipient:String,parcel: Parcel) {
        send(BeCancel(recipient,parcel))
    }
    fun sendBeCleanBag() {
        send(BeCleanBag(Unit))
    }
    override suspend fun act(message: Message) {
        super.act(message)
        when(message) {
            is BeClaimParcel -> beClaimParcel(message.recipient,message.parcel)
            is BeCollect -> beCollect(message.recipient)
            is BeCancel -> beCancel(message.recipient,message.parcel)
            is BeCleanBag -> beCleanBag()
        }
    }
    private suspend fun beClaimParcel(recipient: String, parcel: Parcel) {
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
    private suspend fun beCollect(recipient: Actor) {
        val key = recipient.javaClass.name
        val parcelSet = courierBag[key]?.toHashSet()
        courierBag.remove(key)
        parcelSet?.let { BeCollectCompleted(it) }?.let { send(it) }
    }
    private suspend fun beCancel(recipient:String,parcel: Parcel) {
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
    private suspend fun beCleanBag() {
        courierBag.clear()
    }
}

private data class BeClaimParcel(val recipient: String, val parcel: Parcel): Message
@ObsoleteCoroutinesApi @ExperimentalCoroutinesApi
private data class BeCollect(val recipient: Actor): Message
private data class BeCollectCompleted(val parcelSet: HashSet<Parcel>): Message
private data class BeCancel(val recipient:String,val parcel: Parcel): Message
private data class BeCleanBag(val empty: Unit): Message