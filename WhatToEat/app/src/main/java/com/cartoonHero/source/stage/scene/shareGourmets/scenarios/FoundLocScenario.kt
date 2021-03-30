package com.cartoonHero.source.stage.scene.shareGourmets.scenarios

import com.cartoonHero.source.actors.express.LogisticsCenter
import com.cartoonHero.source.props.enities.GQInputObject
import com.cartoonHero.source.props.enities.Parcel
import com.cartoonHero.source.props.enities.initGQInputObject
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class FoundLocScenario: Actor() {
    fun toBeCollectParcel(complete:(List<GQInputObject>) -> Unit) {
        send {
            beCollectParcel(complete)
        }
    }
    fun toBePrepareNewParcel(inputObj: GQInputObject) {
        send {
            bePrepareNewParcel(inputObj)
        }
    }
    fun toBePackageParcel(inputObj: GQInputObject) {
        send {
            bePackageParcel(inputObj)
        }
    }

    private fun beCollectParcel(complete:(List<GQInputObject>) -> Unit) {
        LogisticsCenter.collectParcels(this) {
            if (it.count() > 0) {
                for (parcel in it) {
                    val parcelItem: Parcel = parcel
                    if (parcelItem.content is List<*>) {
                        complete(parcel.content as List<GQInputObject>)
                    }
                }
            }
        }
    }
    private fun bePrepareNewParcel(inputObj: GQInputObject) {
        val newData = initGQInputObject()
        newData.address = inputObj.address
        toBePackageParcel(newData)
    }
    private fun bePackageParcel(inputObj: GQInputObject) {
        LogisticsCenter.applyExpressService(
            this,
            ShareGourmetScenario().javaClass.name,inputObj)
    }
}