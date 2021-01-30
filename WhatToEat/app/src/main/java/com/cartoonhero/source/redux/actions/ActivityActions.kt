package com.cartoonhero.source.redux.actions

import androidx.fragment.app.Fragment
import org.rekotlin.Action

class GoForwardAction constructor(val fragments: List<Fragment>): Action