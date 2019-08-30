package ismaeldivita.audioma.app.screens.main

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.DaggerAppCompatActivity
import ismaeldivita.audioma.app.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    private val toogleMenu by lazy { hide_menu }
    private val tooglePlayer by lazy { hide_player }
    private val toogleBoth by lazy { hide_both }
    private val playerView by lazy { player }
    private val menuView by lazy { menu }
    private val rootView by lazy { root }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toogleMenu.setOnClickListener {
            TransitionManager.beginDelayedTransition(rootView)
            menuView.visibility = if (menuView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        tooglePlayer.setOnClickListener {
            TransitionManager.beginDelayedTransition(rootView)
            playerView.visibility = if (playerView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        toogleBoth.setOnClickListener {
            BottomSheetBehavior.from(playerView).state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

}