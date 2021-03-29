package com.cartoonHero.source.redux.actions

import android.view.View
import org.rekotlin.Action

class ViewOnClickAction constructor(
    val clickedView: View): Action

class ItemEditTextDidChangedAction constructor(
    val itemView: View, val text: String): Action