package com.cartoonHero.source.whatToEat

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.cartoonHero.source.agent.ActivityStateListener
import com.cartoonHero.source.redux.actions.ActivityOnBackPressed
import com.cartoonHero.source.redux.actions.SceneGoBackAction
import com.cartoonHero.source.redux.actions.SceneGoForwardAction
import com.cartoonHero.source.redux.actions.SetRootSceneAction
import com.cartoonHero.source.redux.appStore
import com.cartoonHero.source.redux.states.ActivityState
import com.cartoonHero.source.stage.scene.NavigationActivity
import com.cartoonHero.source.stage.scene.entrance.fragments.OptionalFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.rekotlin.StoreSubscriber

const val RootSceneBundleKey = "ROOT_SCENE_BUNDLE_KEY"

class MainActivity : NavigationActivity(), StoreSubscriber<ActivityState?> {

    private val stateListeners = hashSetOf<ActivityStateListener>()

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val sceneName = intent.getStringExtra(RootSceneBundleKey)
        if (sceneName.isNullOrEmpty()) {
            setRootFragment(OptionalFragment(),R.id.main_container)
        }
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#FF4D40")
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

    override fun onSupportNavigateUp(): Boolean {
        goBack()
        return super.onSupportNavigateUp()
    }
    override fun onBackPressed() {
        goBack()
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
    fun goForward(fragments: List<Fragment>) {
        goForward(fragments,R.id.main_container)
    }
    private fun goBack() {
        goBack(R.id.main_container)
    }
    fun backToPage(page:Int) {
        backToPage(page,R.id.main_container)
    }

    override fun newState(state: ActivityState?) {
        state.apply {
            when(state?.currentAction) {
                is SetRootSceneAction -> {
                    val action = state.currentAction as SetRootSceneAction
                    setRootFragment(action.rootFragment, R.id.main_container)
                }
                is SceneGoForwardAction -> {
                    val action = state.currentAction as SceneGoForwardAction
                    goForward(action.fragments, R.id.main_container)
                }
                is SceneGoBackAction -> {
                    goBack(R.id.main_container)
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
