package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.dataManger.FragmentContainerTemplate
import com.cartoonhero.source.actors.dataManger.TabMenuTemplate
import com.cartoonhero.source.actors.toolMan.inlineCls.addFragment
import com.cartoonhero.source.actors.toolMan.inlineCls.findFragment
import com.cartoonhero.source.actors.toolMan.inlineCls.removeFragment
import com.cartoonhero.source.actors.toolMan.inlineCls.replaceFragment
import com.cartoonhero.source.whattoeat.R
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
        val oldFragment = attachedActivity.findFragment(this.fragmentContainer.id)
        val newContainerId = View.generateViewId()
        if (oldFragment == null) {
            this.fragmentContainer.id = View.generateViewId()
            attachedActivity.addFragment(template.fragment,newContainerId)
        } else {
            attachedActivity.removeFragment(oldFragment)
            this.fragmentContainer.id = View.generateViewId()
            attachedActivity.replaceFragment(template.fragment,newContainerId)
        }
    }

}