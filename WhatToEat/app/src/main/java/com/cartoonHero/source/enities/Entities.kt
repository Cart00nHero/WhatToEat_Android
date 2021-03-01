package com.cartoonHero.source.enities

data class Parcel(
    val contentType: String,
    var sender: String = "",
    var content: Any
)