package fr.o80.soul

import fr.o80.soul.loop.GameLoop
import fr.o80.soul.scenes.GrayscaleScene
import fr.o80.soul.scenes.MainScene

class SceneManager(
    private val gameLoop: GameLoop
) {

    fun start() {
        gameLoop.open(MainScene(this))
    }

    fun openGrayscaleScene(scale: Float) {
        gameLoop.open(GrayscaleScene(this, scale))
    }

    fun quit() {
        gameLoop.stop()
    }

}
