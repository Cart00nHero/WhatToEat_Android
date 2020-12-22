package com.cartoonhero.source.stage.scenery.templates

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.toolMan.inlineCls.addFragment
import com.cartoonhero.source.actors.toolMan.inlineCls.removeFragment
import com.cartoonhero.source.actors.toolMan.inlineCls.replaceFragment
import com.cartoonhero.source.whattoeat.R

class FragmentContainerLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var attachedActivity: AppCompatActivity
    lateinit var fragment: Fragment
    init {
        inflate(context, R.layout.layout_fragment_container, this)
    }

    open fun initializeLayout() {
        attachedActivity.addFragment(fragment, R.id.fragmentContent)
    }
    fun replace(fragment: Fragment) {
        attachedActivity.replaceFragment(fragment,R.id.fragmentContent)
    }
    fun remove(fragment: Fragment) {
        attachedActivity.removeFragment(fragment)
    }

}