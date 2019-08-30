package ismaeldivita.audioma.app.screens.main

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerAppCompatActivity
import ismaeldivita.audioma.app.R
import ismaeldivita.audioma.app.screens.main.player.PlayerBehavior
import ismaeldivita.audioma.design.coordinator.SmoothScrollBehavior
import kotlinx.android.synthetic.main.activity_main.*

class PlayerActivity : DaggerAppCompatActivity() {

    private val bottomMenu by lazy { menu }
    private val mediaPlayer by lazy { player }
    private val menuBehavior by lazy { SmoothScrollBehavior.from(bottomMenu) }
    private val playerBehavior by lazy { PlayerBehavior.from(mediaPlayer) }

    private var peekHeight: Int = 0
    private var peekHeightWithoutMenu: Int = 0
    private var playerHeight: Int = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)

        bottomMenu.doOnPreDraw {
            peekHeight = bottomMenu.height + playerHeight
            peekHeightWithoutMenu = if (bottomMenu.paddingBottom > 0) {
                bottomMenu.height - bottomMenu.paddingBottom + playerHeight
            } else {
                playerHeight
            }
            playerBehavior.peekHeight = peekHeight
        }

        var menuH = false
        var playerH = false

        menuBehavior.listener = {
            TransitionManager.beginDelayedTransition(
                mediaPlayer.parent as ViewGroup,
                ChangeBounds()
            )
            if (it == SmoothScrollBehavior.State.STATE_SCROLLED_DOWN) {
                playerBehavior.peekHeight = peekHeightWithoutMenu
            } else {
                playerBehavior.peekHeight = peekHeight

            }
        }

        hide_menu.setOnClickListener {
            if (menuH) {
                menuH = false
                showMenu()
            } else {
                menuH = true
                hideMenu()
            }
        }

        hide_player.setOnClickListener {
            if (playerH) {
                playerH = false
                showPlayer()
            } else {
                playerH = true
                hidePlayer()
            }
        }

        hide_both.setOnClickListener {
            if (playerH) {
                playerH = false
                menuH = false
                showPlayer()
                showMenu()
            } else {
                playerH = true
                menuH = true
                hidePlayer()
                hideMenu()
            }
        }
    }

    private fun hideMenu() = menuBehavior.slideDown(bottomMenu)

    private fun showMenu() = menuBehavior.slideUp(bottomMenu)

    private fun hidePlayer() = playerBehavior.nestedScrollBehavior.slideDown(mediaPlayer)

    private fun showPlayer() = playerBehavior.nestedScrollBehavior.slideUp(mediaPlayer)

}