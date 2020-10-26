package fr.o80.soul

import fr.o80.soul.loop.GameLoop

class SoulGame : Game() {

    override val windowName: String = "Soul Game"
    override val updatesPerSecond: Int = 30
    override val width: Int = 1024
    override val height: Int = 768

    override fun createSceneManager(gameLoop: GameLoop): SceneManager =
        SoulSceneManager(gameLoop)

}