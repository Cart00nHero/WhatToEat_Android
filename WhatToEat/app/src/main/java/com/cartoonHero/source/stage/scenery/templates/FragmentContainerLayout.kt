package com.cartoonHero.source.stage.scenery.templates

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.cartoonHero.source.enities.FragmentContainerTemplate
import com.cartoonHero.source.dslMethods.findFragment
import com.cartoonHero.source.dslMethods.removeFragment
import com.cartoonHero.source.dslMethods.replaceFragment
import com.cartoonHero.source.whatToEat.R
import kotlinx.android.synthetic.main.layout_fragment_container.view.*

class FragmentContainerLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var attachedActivity: AppCompatActivity
    lateinit var template: FragmentContainerTemplate

    init {
        inflate(context, R.layout.layout_fragment_container, this)
    }

    open fun initializeLayout() {
        val oldFragment = attachedActivity.findFragment(template.containerId)
        if (oldFragment != null) {
            attachedActivity.removeFragment(oldFragment)
        }
        val containerId = template.containerId
        this.fragment_container.id = containerId
        attachedActivity.replaceFragment(template.fragment,containerId)
    }
}