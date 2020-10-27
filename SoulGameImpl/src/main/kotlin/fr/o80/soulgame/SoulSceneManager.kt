package fr.o80.soulgame

import fr.o80.gamelib.SceneManager
import fr.o80.gamelib.loop.GameLoop
import fr.o80.soulgame.scenes.level.LevelScene
import fr.o80.soulgame.scenes.MainScene

class SoulSceneManager(
    private val gameLoop: GameLoop
) : SceneManager {

    override fun start() {
        gameLoop.open(MainScene(this))
    }

    fun openLevel(leveName: String) {
        gameLoop.open(LevelScene(this, leveName))
    }

    fun quit() {
        gameLoop.stop()
    }

}
