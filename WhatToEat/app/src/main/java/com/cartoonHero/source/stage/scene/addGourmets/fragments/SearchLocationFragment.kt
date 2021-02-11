package com.cartoonHero.source.stage.scene.addGourmets.fragments

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.cartoonHero.source.actors.agent.ActivityStateListener
import com.cartoonHero.source.redux.actions.*
import com.cartoonHero.source.redux.appStore
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.addGourmets.presenters.SearchLocationPresenter
import com.cartoonHero.source.whatToEat.MainActivity
import com.cartoonHero.source.whatToEat.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_search_location.*
import type.AddressDqCmd

class SearchLocationFragment: Fragment(), OnMapReadyCallback {

    private val presenter = SearchLocationPresenter()
    private lateinit var mMap: GoogleMap
    private lateinit var mCoverView: View

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
            presenter.createCoverView(requireContext(),bottom_select_view)
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
        top_select_view.setBackgroundColor(presenter.selectedBgColor())
        bottom_select_view.setBackgroundColor(presenter.normalBgColor())
        search_bar_button.setOnClickListener {
            when(presenter.searchMode) {
                SearchLocationPresenter.SearchMode.Map -> {
                    appStore.dispatch(geoCodeAddressAction(
                        requireContext(),search_editText.text.toString()))
                }
                SearchLocationPresenter.SearchMode.Google -> {
                    google_search_webView.visibility = View.VISIBLE
                    val webViewClient = WebViewClient()
                    google_search_webView.webViewClient = webViewClient
                    google_search_webView.loadUrl(
                        presenter.googleSearchUrl(
                            search_editText.text.toString()))
                }
            }
        }
    }

    private val stateChangedListener = object : ActivityStateListener {
        override fun onNewState(state: ActivityState) {
            when(state.currentAction) {
                is ViewOnClickAction -> {
                    val action = state.currentAction as ViewOnClickAction
                    if (action.clickedView.parent === top_select_view) {
                        top_select_view.removeView(mCoverView)
                        top_select_view.setBackgroundColor(presenter.selectedBgColor())
                        presenter.setViewDefaultStyle(bottom_select_view)
                        mCoverView = presenter.createCoverView(context!!,bottom_select_view)
                        presenter.searchMode = SearchLocationPresenter.SearchMode.Map
                    }
                    if (action.clickedView.parent === bottom_select_view) {
                        bottom_select_view.removeView(mCoverView)
                        presenter.setViewSelectedStyle(bottom_select_view)
                        top_select_view.setBackgroundColor(presenter.normalBgColor())
                        mCoverView = presenter.createCoverView(context!!,top_select_view)
                        presenter.searchMode = SearchLocationPresenter.SearchMode.Google
                    }
                }
                is GeoCodeAddressAction -> {
                    val action = state.currentAction as GeoCodeAddressAction
                    when(action.status) {
                        GeoActionStatus.Started -> {
                            // To-Do remove annotation
                            mMap.clear()
                        }
                        GeoActionStatus.Completed -> {
                            val willCheckLoc = Location("Google")
                            willCheckLoc.latitude = action.address?.latitude ?: 0.0
                            willCheckLoc.longitude = action.address?.longitude ?: 0.0
                            appStore.dispatch(reverseLocationAction(context!!,willCheckLoc))
                        }
                        else -> {}
                    }
                }
                is ReverseLocationAction -> {
                    val action = state.currentAction as ReverseLocationAction
                    if (action.address != null) {
                        appStore.dispatch(ParseAndQueryLocAction(true,action.address!!))
                    }
                }
                is LocationsDynamicQueryAction -> {
                    val action = state.currentAction as LocationsDynamicQueryAction
                    when(action.status) {
                        NetWorkStatus.SUCCESS -> {
                            if (action.responseData?.size ?: 0 > 0) {
                                print("parse to input")
                            }
                        }
                        else -> {}
                    }
                }
                is MarLocationOnMapAction -> {
                    val action = state.currentAction as MarLocationOnMapAction
                    if (action.status == GeoActionStatus.Completed) {
                        for (marker in action.annotations) {
                            mMap.addMarker(marker)
                        }
                    }
                }
            }
        }
    }
}