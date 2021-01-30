package com.cartoonHero.source.redux

import com.cartoonHero.source.redux.reducers.appReducer
import org.rekotlin.Store

val appStore = Store (
    reducer = ::appReducer,
    state = null
)
