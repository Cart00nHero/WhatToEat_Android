package com.cartoonHero.source.stage.scene.addGourmets.scenarios

import com.cartoonHero.source.actors.express.LogisticsCenter
import com.cartoonHero.source.props.enities.GQInputObject
import com.cartoonHero.source.props.enities.Parcel
import com.cartoonHero.source.props.enities.initGQInputObject
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AddGourmetScenario: Actor() {
    private var queryData = initGQInputObject()

    fun toBeCollectGQInputParcel() {
        send {
            beCollectGQInputParcel()
        }
    }
    fun toBeGetInputData(complete: (GQInputObject) -> Unit) {
        send {
            beGetInputData(complete)
        }
    }
    fun toBeUpdateInputData(newInput: GQInputObject) {
        send {
            beUpdateInputData(newInput)
        }
    }
    private fun beCollectGQInputParcel() {
        LogisticsCenter.collectParcels(this) {
            if (it.count() > 0) {
                for (parcel in it) {
                    val parcelItem: Parcel = parcel
                    if (parcelItem.content is GQInputObject) {
                        queryData = parcelItem.content as GQInputObject
                    }
                }
            }
        }
    }
    private fun beGetInputData(complete: (GQInputObject) -> Unit) {
        complete(queryData)
    }
    private fun beUpdateInputData(newInput: GQInputObject) {
        queryData = newInput
    }
}