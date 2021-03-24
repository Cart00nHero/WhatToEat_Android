package com.cartoonHero.source.props.enities

import type.InputAddress
import type.InputBranch
import type.InputCoordinate
import type.InputShop

data class GQInputObject (
    var shopId: String = "",
    var branchId: String = "",
    var address: InputAddress,
    var shopBranch: InputBranch,
    var shop: InputShop
)

data class GQSearchRange(
    val max: InputCoordinate,
    val min: InputCoordinate
)

fun initGQInputObject(): GQInputObject {
    return GQInputObject(
        shopId = "",
        branchId = "",
        shop = initInputShop(),
        shopBranch = initInputBranch(),
        address = initInputAddress()
    )
}

fun initInputShop(): InputShop {
    return InputShop(
        title = "",
        underPrice = 0.0
    )
}
fun initInputBranch(): InputBranch {
    return InputBranch(
        name = ""
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
