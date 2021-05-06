package fr.o80.soulgame

import fr.o80.gamelib.Scene
import fr.o80.gamelib.SceneManager
import fr.o80.gamelib.loop.GameLoop
import fr.o80.soulgame.scenes.gameover.GameOverInfo
import fr.o80.soulgame.scenes.gameover.GameOverScene
import fr.o80.soulgame.scenes.level.LevelScene
import fr.o80.soulgame.scenes.main.MainScene

class SoulSceneManager(
    private val gameLoop: GameLoop
) : SceneManager {

    override val initialScene: Scene
        get() = MainScene(this)

    fun openLevel(leveName: String) {
        gameLoop.open(LevelScene(this, leveName))
    }

    fun quit() {
        gameLoop.stop()
    }

    fun openGameOver(levelName: String, score: Long) {
        gameLoop.open(
            GameOverScene(
                this,
                GameOverInfo(levelName, score)
            )
        )
    }

}
