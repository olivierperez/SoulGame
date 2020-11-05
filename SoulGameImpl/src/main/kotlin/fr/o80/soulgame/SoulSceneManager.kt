package fr.o80.soulgame

import fr.o80.gamelib.SceneManager
import fr.o80.gamelib.loop.GameLoop
import fr.o80.soulgame.scenes.level.LevelScene
import fr.o80.soulgame.scenes.MainScene
import fr.o80.soulgame.scenes.gameover.GameOverScene

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

    fun openGameOver(score: Long) {
        gameLoop.open(GameOverScene(this, score))
    }

}
