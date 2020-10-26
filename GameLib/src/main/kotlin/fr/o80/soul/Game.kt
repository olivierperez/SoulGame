package fr.o80.soul

import fr.o80.soul.loop.GameLoop
import kotlinx.coroutines.runBlocking

abstract class Game {

    abstract val windowName: String
    abstract val updatesPerSecond: Int
    abstract val width: Int
    abstract val height: Int
    abstract fun createSceneManager(gameLoop: GameLoop): SceneManager

    fun start() = runBlocking {
        val gameLoop = GameLoop(width, height, updatesPerSecond, windowName)
        val sceneManager = createSceneManager(gameLoop)

        sceneManager.start()
        gameLoop.start()
    }

}