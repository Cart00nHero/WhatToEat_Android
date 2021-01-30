package com.cartoonhero.source.stage.scenery

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.toolMan.inlineCls.addFragment
import com.cartoonhero.source.actors.toolMan.inlineCls.removeFragment
import com.cartoonhero.source.actors.toolMan.inlineCls.replaceFragment

@SuppressLint("Registered")
open class NavigationActivity : AppCompatActivity() {

    private var currentPage = 0
    private val fragmentList = ArrayList<Fragment>()

    fun setRootFragment(fragment: Fragment, resourceId: Int) {
        if (fragmentList.size > 0) {
            removeFragment(currentFragment())
            fragmentList.clear()
        }
        addFragment(fragment, resourceId)
        fragmentList.add(0, fragment)
    }

    fun goForward(fragments: List<Fragment>, resourceId: Int) {
        currentPage += fragments.size
        fragmentList.addAll(fragments)
        replaceFragment(fragments.last(), resourceId)
    }

    fun goBack(resourceId: Int) {
        if (currentPage > 0) {
            val currentFragment = fragmentList[currentPage]
            val previousFragment = fragmentList[currentPage-1]
            fragmentList.remove(currentFragment)
            currentPage -= 1
            replaceFragment(previousFragment, resourceId)
        } else {
            finish()
        }
    }
    fun backToPage(index: Int, resourceId: Int) {
        if (currentPage > index) {
            replaceFragment(fragmentList[index], resourceId)
            fragmentList.dropLast( (currentPage-index) )
        }
    }

    fun currentFragment(): Fragment{
        return fragmentList.last()
    }

    fun childFragments(): List<Fragment> {
        return fragmentList.clone() as List<Fragment>
    }

    private fun clearFragmentBackStack() {
        if (supportFragmentManager.fragments.size > 0) {
            for (fragment in supportFragmentManager.fragments) {
                supportFragmentManager.beginTransaction().remove(fragment!!).commit()
            }
        }
    }
}