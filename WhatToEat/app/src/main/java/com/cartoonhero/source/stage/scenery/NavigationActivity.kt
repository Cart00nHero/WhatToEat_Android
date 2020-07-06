package com.cartoonhero.source.stage.scenery

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cartoonhero.source.actors.inlineCls.addFragment
import com.cartoonhero.source.actors.inlineCls.replaceFragment

@SuppressLint("Registered")
open class NavigationActivity : AppCompatActivity() {

    var currentPage = 0
    private val fragmentList = ArrayList<Fragment>()

    fun initFragment(fragment: Fragment, resourceId: Int) {
        addFragment(fragment, resourceId)
        fragmentList.add(0, fragment)
    }

    fun goForwardPage(fragments: List<Fragment>, resourceId: Int) {
        currentPage += fragments.size
        fragmentList.addAll(fragments)
        replaceFragment(fragments.last(), resourceId)
    }

    fun backToPreviousPage(resourceId: Int) {
        if (currentPage > 0) {
            val currentFragment = fragmentList[currentPage]
            val previosFragment = fragmentList[currentPage-1]
            fragmentList.remove(currentFragment)
            currentPage -= 1
            replaceFragment(previosFragment, resourceId)
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
}