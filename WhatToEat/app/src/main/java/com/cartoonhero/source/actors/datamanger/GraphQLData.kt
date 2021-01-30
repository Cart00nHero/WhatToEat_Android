package com.cartoonhero.source.actors.dataManger

import com.apollographql.apollo.api.Input
import type.InputAddress
import type.InputBranch
import type.InputShop

data class GQCreateObject (
    var shopId: String = "",
    var branchId: String = "",
    var address: InputAddress,
    var shopBranch: InputBranch,
    var shop: InputShop
)
data class GQUpdateObject (
    var shopId: String = "",
    var branchId: String = "",
    var address: Input<InputAddress>,
    var shopBranch: InputBranch,
    var shop: Input<InputShop>
)