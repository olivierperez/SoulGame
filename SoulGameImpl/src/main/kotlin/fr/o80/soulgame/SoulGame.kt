package fr.o80.soulgame

import fr.o80.gamelib.Game
import fr.o80.gamelib.SceneManager
import fr.o80.gamelib.loop.GameLoop
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.service.cursor.CursorManager

class SoulGame : Game() {

    override val windowName: String = "Soul Game"
    override val updatesPerSecond: Int = 30
    override val width: Int = 1120
    override val height: Int = 840
    override val debug: Boolean = false

    override fun createSceneManager(gameLoop: GameLoop): SceneManager = SoulSceneManager(gameLoop)
    override fun createCursorManager(window: Window): CursorManager = SoulCursorManager(window)

}