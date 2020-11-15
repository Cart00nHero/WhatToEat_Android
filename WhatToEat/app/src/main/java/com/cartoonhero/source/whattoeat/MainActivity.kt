package com.cartoonhero.source.whattoeat

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cartoonhero.source.actors.agent.ActivityStateListener
import com.cartoonhero.source.redux.appStore
import com.cartoonhero.source.redux.states.ActivityState
import com.cartoonhero.source.stage.scene.addGourmets.fragments.AddGourmetFragment
import com.cartoonhero.source.stage.scenery.NavigationActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.rekotlin.StoreSubscriber

class MainActivity : NavigationActivity(), StoreSubscriber<ActivityState?> {

//    lateinit var receiveNewState: (state: ActivityState) -> Unit
    var mListener: ActivityStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initFragment(AddGourmetFragment(),R.id.contentFrameLayout)
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

    fun addStateListener(listener: ActivityStateListener) {
        this.mListener = listener
    }
    fun removeListener() {
        this.mListener = null
    }

    override fun newState(state: ActivityState?) {
        state.apply {
//            state?.let { receiveNewState(it) }
            state?.let { this@MainActivity.mListener?.onNewState(it) }
        }
    }
}
