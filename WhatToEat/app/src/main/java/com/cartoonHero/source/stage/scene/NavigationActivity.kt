package com.cartoonHero.source.stage.scene

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cartoonHero.source.props.addFragment
import com.cartoonHero.source.props.removeFragment
import com.cartoonHero.source.props.replaceFragment

open class NavigationActivity : AppCompatActivity() {

    private var currentPage = 0
    private val fragmentList = ArrayList<Fragment>()

    fun setRootFragment(fragment: Fragment, resourceId: Int) {
        if (fragmentList.size > 0) {
            removeFragment(currentFragment())
            fragmentList.clear()
            clearFragmentBackStack()
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

    private fun currentFragment(): Fragment{
        return fragmentList.last()
    }

    fun childFragments(): List<Fragment> {
        return fragmentList.toList()
    }

    private fun clearFragmentBackStack() {
        if (supportFragmentManager.fragments.size > 0) {
            for (fragment in supportFragmentManager.fragments) {
                supportFragmentManager.beginTransaction().remove(fragment!!).commit()
            }
        }
    }
}