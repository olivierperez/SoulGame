package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.dsl.alpha
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Window

class PauseOverlayRenderer(
    private val window: Window
) {

    fun render() {
        draw {
            alpha {
                color(0f, 0f, 0f, 0.4f)
                quad(0f, 0f, window.width.toFloat(), window.height.toFloat())
            }
        }
    }
}
