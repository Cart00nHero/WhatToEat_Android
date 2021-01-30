package com.cartoonHero.source.redux.actions

import androidx.fragment.app.Fragment
import org.rekotlin.Action

class SetRootSceneAction constructor(
    val rootFragment: Fragment): Action
class SceneGoForwardAction constructor(
    val fragments: List<Fragment>): Action
class SceneGoBackAction : Action
class ActivityOnBackPressed : Action