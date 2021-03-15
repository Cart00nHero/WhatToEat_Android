package com.cartoonHero.source.redux.actions

import com.cartoonHero.source.props.enities.GQInputObject
import com.google.android.gms.maps.model.MarkerOptions
import org.rekotlin.Action

class MapRemoveAllAnnotationsAction: Action
class MapClearAndShowMarkersAction constructor(
    val markers: List<MarkerOptions>):Action
class FoundLocationsAddressAction constructor(
    val inputObj: GQInputObject): Action