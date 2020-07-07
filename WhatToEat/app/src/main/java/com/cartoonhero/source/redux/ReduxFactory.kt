package com.cartoonhero.source.redux

import com.cartoonhero.source.redux.reducers.appReducer
import org.rekotlin.Store

val appStore = Store (
    reducer = ::appReducer,
    state = null
)
