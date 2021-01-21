package fr.o80.soulgame.scenes.main

import fr.o80.gamelib.CursorManager
import fr.o80.gamelib.Scene
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipeline
import fr.o80.gamelib.loop.MouseMovePipeline
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.menu.Menu
import fr.o80.gamelib.menu.TextResources
import fr.o80.soulgame.SoulCursorManager
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground

class MainScene(
    private val sceneManager: SoulSceneManager
) : Scene {
    private lateinit var menu: Menu

    override fun open(
        window: Window,
        cursorManager: CursorManager,
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipeline,
        mouseMovePipeline: MouseMovePipeline
    ) {
        cursorManager.setCursor(SoulCursorManager.POINTER)

        menu = Menu.MenuBuilder()
            .of(
                top = .0,
                left = .0,
                right = window.width.toDouble(),
                bottom = window.height.toDouble()
            )
            .andResources(
                background = greenBackground,
                textResources = TextResources(
                    font = resource("fonts/LaserCutRegular.ttf"),
                    fontHeight = 50f
                ),
                titleResources = TextResources(
                    font = resource("fonts/LaserCutRegular.ttf"),
                    fontHeight = 99f
                )
            )
            .withPipelines(
                mouseButtonPipeline,
                mouseMovePipeline
            )
            .andLayout {
                title("Soul Game", verticalMargin = 50.0)
                button("Start") {
                    sceneManager.openLevel("level_1")
                }
                button("Quit") {
                    sceneManager.quit()
                }
            }
            .build()
    }

    override fun close() {
        menu.close()
    }

    override suspend fun update() {
        menu.update()
    }

    override suspend fun render() {
        menu.render()
    }
}