package com.cartoonHero.source.stage.scene.shareGourmets.scenarios

import com.cartoonHero.source.actors.express.LogisticsCenter
import com.cartoonHero.source.props.convertAnyToJson
import com.cartoonHero.source.props.enities.*
import com.cartoonHero.source.props.toAny
import com.cartoonHero.source.props.toJson
import com.cartoonHero.source.props.toMap
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import type.InputAddress
import type.InputBranch

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class ShareGourmetScenario: Actor() {
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
    fun toBeUpdateInputData(newValue: String, posPath: ConcatPosition) {
        send {
            beUpdateInputData(newValue,posPath)
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
    private fun beUpdateInputData(newValue: String, posPath: ConcatPosition) {
        val newInput = queryData
        if (posPath.section == 0) {
            val branchMap =
                convertAnyToJson(queryData.shopBranch).toMap<Any>()?.toMutableMap()
            when(posPath.position) {
                1 -> branchMap?.set("title", newValue)
                2 -> branchMap?.set("subtitle",newValue)
                3 -> branchMap?.set("underPrice",newValue.toFloat())
                4 -> branchMap?.set("tel",newValue)
            }
            newInput.shopBranch =
                branchMap?.toJson()?.toAny<InputBranch>() ?: initInputBranch()
        }
        if (posPath.section == 1) {
            val locMap = convertAnyToJson(queryData.address).toMap<Any>()?.toMutableMap()
            when(posPath.position) {
                2 -> locMap?.set("floor", newValue)
                3 -> locMap?.set("room",newValue)
            }
            newInput.address =
                locMap?.toJson()?.toAny<InputAddress>() ?: initInputAddress()
        }
        queryData = newInput
    }
}