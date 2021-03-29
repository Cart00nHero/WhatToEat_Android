package com.cartoonHero.source.props.enities

import type.InputAddress
import type.InputBranch
import type.InputCoordinate

data class GQInputObject (
    var branchId: String = "",
    var address: InputAddress,
    var shopBranch: InputBranch
)

data class GQSearchRange(
    val max: InputCoordinate,
    val min: InputCoordinate
)

fun initGQInputObject(): GQInputObject {
    return GQInputObject(
        branchId = "",
        shopBranch = initInputBranch(),
        address = initInputAddress()
    )
}
fun initInputBranch(): InputBranch {
    return InputBranch(
        title = ""
    )
}
fun initInputAddress(): InputAddress {
    return InputAddress(
        completeInfo = "",
        nation = "",
        isoNationCode = "",
        locality = "",
        subLocality = "",
        administrativeArea = "",
        subAdministrativeArea = "",
        thoroughfare = "",
        subThoroughfare = "",
        latitude = 0.0,
        longitude = 0.0
    )
}
