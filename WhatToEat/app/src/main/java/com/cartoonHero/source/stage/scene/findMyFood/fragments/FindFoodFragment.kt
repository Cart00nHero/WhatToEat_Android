package com.cartoonHero.source.stage.scene.findMyFood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonHero.source.whatToEat.R
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.fragment_find_food.*

class FindFoodFragment: Fragment() {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find_food,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fmf_mapView.onCreate(savedInstanceState)
        fmf_mapView.getMapAsync {
            fmf_mapView.onStart()
            mMap = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fmf_mapView.onDestroy()
    }

    private inner class RecyclerAdapter: BounceRecyclerAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BounceViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: BounceViewHolder, position: Int) {
            onHolderBound(holder.itemView,position)
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }
        private fun onHolderBound(itemView: View, position: Int) {

        }
    }
}