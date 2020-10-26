package fr.o80.soul

import fr.o80.soul.loop.GameLoop
import fr.o80.soul.scenes.GrayscaleScene
import fr.o80.soul.scenes.MainScene

class SoulSceneManager(
    private val gameLoop: GameLoop
) : SceneManager {

    override fun start() {
        gameLoop.open(MainScene(this))
    }

    fun openGrayscaleScene(scale: Float) {
        gameLoop.open(GrayscaleScene(this, scale))
    }

    fun quit() {
        gameLoop.stop()
    }

}
