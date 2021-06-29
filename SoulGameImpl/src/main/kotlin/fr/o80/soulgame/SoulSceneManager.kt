package fr.o80.soulgame

import fr.o80.gamelib.Scene
import fr.o80.gamelib.SceneManager
import fr.o80.gamelib.loop.GameLoop
import fr.o80.soulgame.scenes.gameover.GameOverInfo
import fr.o80.soulgame.scenes.gameover.GameOverScene
import fr.o80.soulgame.scenes.level.LevelScene
import fr.o80.soulgame.scenes.level.level.LevelSettings
import fr.o80.soulgame.scenes.levelselector.LevelSelectorScene
import fr.o80.soulgame.scenes.main.MainScene

class SoulSceneManager(
    private val gameLoop: GameLoop
) : SceneManager {

    override val initialScene: Scene
        get() = MainScene(this)

    fun openMain() {
        gameLoop.open(MainScene(this))
    }

    fun openLevel(levelCode: Int) {
        gameLoop.open(
            LevelScene(this, levelCode)
        )
    }

    fun openGameOver(levelSettings: LevelSettings, score: Long) {
        gameLoop.open(
            GameOverScene(
                this,
                GameOverInfo(levelSettings, score)
            )
        )
    }

    fun openLevelSelector() {
        gameLoop.open(
            LevelSelectorScene(this)
        )
    }

    fun quit() {
        gameLoop.stop()
    }

}
