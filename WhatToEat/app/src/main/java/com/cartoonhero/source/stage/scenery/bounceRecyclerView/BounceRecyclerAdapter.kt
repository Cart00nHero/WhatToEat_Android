package com.cartoonhero.source.stage.scenery.bounceRecyclerView

import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView

open class BounceRecyclerAdapter(): RecyclerView.Adapter<BounceRecyclerAdapter.ViewHolder>() {

    interface ViewHolderListener {
        fun onBindViewHolder(itemView: View)
    }
    private var mListener: ViewHolderListener? = null

    fun addViewHolderListener(listener: ViewHolderListener) {
        if (mListener == null) {
            mListener = listener
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mListener?.let { holder.bindViewHolderListener(it) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val springAnimY: SpringAnimation = SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y)
            .setSpring(SpringForce().apply {
                finalPosition = 0f
                dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                stiffness = SpringForce.STIFFNESS_VERY_LOW
            })
        fun bindViewHolderListener(listener: ViewHolderListener) {
            listener.onBindViewHolder(itemView)
        }
    }
}