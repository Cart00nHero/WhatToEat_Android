package com.cartoonhero.source.whattoeat

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cartoonhero.source.actors.agent.ActivityStateDelegate
import com.cartoonhero.source.redux.appStore
import com.cartoonhero.source.redux.states.ActivityState
import com.cartoonhero.source.stage.scene.entrance.SignFragment
import com.cartoonhero.source.stage.scenery.NavigationActivity
import com.cartoonhero.source.stage.scenery.bounceRecyclerView.BounceRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.rekotlin.StoreSubscriber

class MainActivity : NavigationActivity(), StoreSubscriber<ActivityState?> {

//    lateinit var receiveNewState: (state: ActivityState) -> Unit
    var delegate: ActivityStateDelegate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initFragment(SignFragment(),R.id.contentFrameLayout)
    }

    override fun onStart() {
        super.onStart()
        appStore.subscribe(this) {
            it.select { it ->
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
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
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

//    fun receiveNewStateListener(callback: (ActivityState) -> Unit) {
//        receiveNewState = callback
//    }
    fun setOnStateChangedDelegate(delegate: ActivityStateDelegate) {
        this.delegate = delegate
    }
    fun removeDelegate() {
        this.delegate = null
    }

    override fun newState(state: ActivityState?) {
        state.apply {
//            state?.let { receiveNewState(it) }
            state?.let { this@MainActivity.delegate?.receiveNewState(it) }
        }
    }
}
