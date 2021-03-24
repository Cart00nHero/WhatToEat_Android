package com.cartoonHero.source.stage.scenery.specialEffects.bounceRecyclerView

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.helper.ItemTouchHelperAdapter

abstract class BounceRecyclerAdapter :
    RecyclerView.Adapter<BounceRecyclerAdapter.BounceViewHolder>(),
    ItemTouchHelperAdapter {

    interface ViewHolderListener {
        fun onHolderBound(itemView: View, position: Int)
    }
    private var mListener: ViewHolderListener? = null

    fun addViewHolderListener(listener: ViewHolderListener) {
        if (mListener == null) {
            mListener = listener
        }
    }

    abstract class BounceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val springAnimY: SpringAnimation = SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y)
            .setSpring(SpringForce().apply {
                finalPosition = 0f
                dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                stiffness = SpringForce.STIFFNESS_VERY_LOW
            })
        fun bindViewHolderListener(listener: ViewHolderListener,position: Int) {
            listener.onHolderBound(itemView,position)
        }
    }
}