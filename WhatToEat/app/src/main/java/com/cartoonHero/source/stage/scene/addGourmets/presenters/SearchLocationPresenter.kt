package com.cartoonHero.source.stage.scene.addGourmets.presenters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cartoonHero.source.extensionTools.toDp
import com.cartoonHero.source.actors.toolMan.match
import com.cartoonHero.source.redux.actions.ViewOnClickAction
import com.cartoonHero.source.redux.appStore

class SearchLocationPresenter {
    enum class SearchMode {
        Map, Google
    }
    var searchMode: SearchMode = SearchMode.Map

    fun googleSearchUrl(queryText: String): String {
        val query = queryText.replace(" ","+")
        return "https://www.google.co.in/search?q=$query"
    }
    fun createCoverView(
        context: Context, coverSuperLayout: ConstraintLayout): View {
        val coverView = View(context)
        coverView.id = View.generateViewId()
        coverView.setBackgroundColor(Color.parseColor("#00000000"))
        coverSuperLayout.addView(coverView)
        val set = ConstraintSet()
        set.clone(coverSuperLayout)
        set.match(coverView, coverSuperLayout)
        coverView.setOnClickListener {
            appStore.dispatch(ViewOnClickAction(it))
        }
        return coverView
    }
    fun setViewDefaultStyle(selectView: View) {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(normalBgColor())
        gradientDrawable.setStroke(
            2.toDp(selectView.context),normalBgColor())
        selectView.background = gradientDrawable
    }
    fun setViewSelectedStyle(selectView: View) {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(normalBgColor())
        gradientDrawable.setStroke(
            2.toDp(selectView.context),selectedBgColor())
        selectView.background = gradientDrawable
    }
    fun normalBgColor(): Int{
        return Color.parseColor("#F5FFFA")
    }
    fun selectedBgColor():Int {
        return Color.RED
    }
}