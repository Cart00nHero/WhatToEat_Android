package com.cartoonHero.source.stage.scene.addGourmets.scenarios

import android.app.Activity
import android.content.Context
import com.cartoonHero.source.actors.pilot.Pilot
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi

class SearchLocationScenario
constructor(context: Context,activity: Activity): Actor() {
    private val mContext = context
    private val mActivity = activity
    private val pilot: Pilot = Pilot(context)

    fun toBeCheckGPSPermission(complete: (Boolean) -> Unit) {
        send {
            beCheckGPSPermission(complete)
        }
    }
    private fun beCheckGPSPermission(complete: (Boolean) -> Unit) {
        pilot.toBeCheckPermission(this,mActivity) {
            CoroutineScope(Dispatchers.Main).launch {
                complete(it)
            }
        }
    }
}