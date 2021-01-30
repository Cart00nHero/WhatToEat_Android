package com.cartoonHero.source.whatToEat

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cartoonHero.source.actors.agent.ActivityStateListener
import com.cartoonHero.source.redux.actions.ActivityOnBackPressed
import com.cartoonHero.source.redux.actions.SceneGoBackAction
import com.cartoonHero.source.redux.actions.SceneGoForwardAction
import com.cartoonHero.source.redux.appStore
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.entrance.SignFragment
import com.cartoonHero.source.stage.scene.NavigationActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.rekotlin.StoreSubscriber

const val mainFragmentContainerId = R.id.mainFragmentContainer

class MainActivity : NavigationActivity(), StoreSubscriber<ActivityState?> {

    private val stateListeners = hashSetOf<ActivityStateListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setRootFragment(SignFragment(),R.id.mainFragmentContainer)
    }

    override fun onStart() {
        super.onStart()
        appStore.subscribe(this) { it ->
            it.select {
                it.activityState
            }
        }
    }

    override fun onStop() {
        super.onStop()
        appStore.unsubscribe(this)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
        toolbar.inflateMenu(R.menu.menu_tool_bar)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        appStore.dispatch(ActivityOnBackPressed())
    }

    fun addStateListener(listener: ActivityStateListener) {
        if (!stateListeners.contains(listener)) {
            stateListeners.add(listener)
        }
    }
    fun removeStateListener(listener: ActivityStateListener) {
        stateListeners.remove(listener)
    }

    override fun newState(state: ActivityState?) {
        state.apply {
            when(state?.currentAction) {
                is SceneGoForwardAction -> {
                    val action = state.currentAction as SceneGoForwardAction
                    goForward(action.fragments, mainFragmentContainerId)
                }
                is SceneGoBackAction -> {
                    goBack(mainFragmentContainerId)
                }
                else -> {
                    for (listener in stateListeners) {
                        state?.let { listener.onNewState(it) }
                    }
                }
            }
        }
    }
}
