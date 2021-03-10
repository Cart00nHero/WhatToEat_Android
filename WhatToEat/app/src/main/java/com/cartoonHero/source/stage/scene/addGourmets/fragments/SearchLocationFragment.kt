package com.cartoonHero.source.stage.scene.addGourmets.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.cartoonHero.source.actors.toolMan.match
import com.cartoonHero.source.agent.ActivityStateListener
import com.cartoonHero.source.dslMethods.toDp
import com.cartoonHero.source.redux.actions.*
import com.cartoonHero.source.redux.appStore
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_search_location.*

class SearchLocationFragment: Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mCoverView: View
    private enum class SearchMode {
        Map, Google
    }
    private var searchMode: SearchMode = SearchMode.Map

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_search_location, container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCoverView =
            createCoverView(requireContext(),bottom_select_view)
        initFragmentView()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity)
            .addStateListener(stateChangedListener)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity)
            .removeStateListener(stateChangedListener)
    }

    private fun initFragmentView() {
        top_select_view.setBackgroundColor(selectedBgColor())
        bottom_select_view.setBackgroundColor(normalBgColor())
        search_bar_button.setOnClickListener {
            when(searchMode) {
                SearchMode.Map -> {
                }
                SearchMode.Google -> {
                    google_search_webView.visibility = View.VISIBLE
                    val webViewClient = WebViewClient()
                    google_search_webView.webViewClient = webViewClient
                    google_search_webView.loadUrl("")
                }
            }
        }
    }
    private fun createCoverView(
        context: Context, coverSuperLayout: ConstraintLayout
    ): View {
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
    private fun setViewDefaultStyle(selectView: View) {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(normalBgColor())
        gradientDrawable.setStroke(
            2.toDp(selectView.context),normalBgColor())
        selectView.background = gradientDrawable
    }
    private fun setViewSelectedStyle(selectView: View) {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(normalBgColor())
        gradientDrawable.setStroke(
            2.toDp(selectView.context),selectedBgColor())
        selectView.background = gradientDrawable
    }
    private fun normalBgColor(): Int{
        return Color.parseColor("#F5FFFA")
    }
    private fun selectedBgColor():Int {
        return Color.RED
    }

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
            when(state.currentAction) {
                is ViewOnClickAction -> {
                    val action = state.currentAction as ViewOnClickAction
                    if (action.clickedView.parent === top_select_view) {
                        top_select_view.removeView(mCoverView)
                        top_select_view.setBackgroundColor(selectedBgColor())
                        setViewDefaultStyle(bottom_select_view)
                        mCoverView = createCoverView(context!!,bottom_select_view)
                        searchMode = SearchMode.Map
                    }
                    if (action.clickedView.parent === bottom_select_view) {
                        bottom_select_view.removeView(mCoverView)
                        setViewSelectedStyle(bottom_select_view)
                        top_select_view.setBackgroundColor(normalBgColor())
                        mCoverView = createCoverView(context!!,top_select_view)
                        searchMode = SearchMode.Google
                    }
                }
            }
        }
    }
}