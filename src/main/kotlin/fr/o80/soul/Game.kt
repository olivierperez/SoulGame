package fr.o80.soul

import fr.o80.soul.loop.GameLoop
import kotlinx.coroutines.runBlocking

class Game {

    fun start() = runBlocking {
        val gameLoop = GameLoop(500, 500, 30, "Soul")
        val sceneManager = SceneManager(gameLoop)

        sceneManager.start()
        gameLoop.start()
    }

}