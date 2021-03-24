package com.cartoonHero.source.actors.mathematician

import android.location.Location
import com.cartoonHero.source.props.enities.GQSearchRange
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class Mathematician: Actor() {
    fun toBeCalculateRange(
        sender: Actor, location: Location, range: Float,
        complete: (GQSearchRange) -> Unit
    ) {
        send {
            beCalculateRange(sender, location, range, complete)
        }
    }
    fun toBeCalculateLocationDistance(
        sender: Actor,from: Location,to: Location,
        complete: (Float) -> Unit) {
        send {
            beCalculateLocationDistance(sender, from, to, complete)
        }
    }
    private fun beCalculateRange(
        sender: Actor, location: Location, range: Float,
        complete: (GQSearchRange) -> Unit
    ) {
        val result = Haversine().calculateRange(location, range)
        sender.send {
            complete(result)
        }
    }
    private fun beCalculateLocationDistance(
        sender: Actor,from: Location,to: Location,complete: (Float) -> Unit) {
        val distance = from.distanceTo(to)
        sender.send {
            complete(distance)
        }
    }
}