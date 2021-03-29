package com.cartoonHero.source.stage.scene.shareGourmets.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView.BounceRecyclerAdapter
import com.cartoonHero.source.whatToEat.R

class FoundLocFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_found_location,container,false)
    }

    private inner class RecyclerAdapter(): BounceRecyclerAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BounceViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: BounceViewHolder, position: Int) {
            onHolderBound(holder.itemView,position)
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        override fun onMoveItem(from: Int, to: Int) {
            TODO("Not yet implemented")
        }

        override fun onRemoveItem(from: Int) {
            TODO("Not yet implemented")
        }

        private fun onHolderBound(itemView: View, position: Int) {
        }
    }
}