package fr.o80.gamelib

import fr.o80.gamelib.loop.GameLoop
import fr.o80.gamelib.loop.Window
import kotlinx.coroutines.runBlocking

abstract class Game {

    abstract val windowName: String
    abstract val updatesPerSecond: Int
    abstract val width: Int
    abstract val height: Int

    abstract fun createSceneManager(gameLoop: GameLoop): SceneManager
    abstract fun createCursorManager(window: Window): CursorManager

    fun start() = runBlocking {
        val gameLoop = GameLoop(this@Game, width, height, updatesPerSecond, windowName)
        val sceneManager = createSceneManager(gameLoop)

        gameLoop.start(sceneManager.initialScene)
    }

}