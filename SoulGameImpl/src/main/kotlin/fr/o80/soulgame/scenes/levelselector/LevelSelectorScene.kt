package fr.o80.soulgame.scenes.levelselector

import fr.o80.gamelib.Scene
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipeline
import fr.o80.gamelib.loop.MouseMovePipeline
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.menu.Menu
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.service.Services
import fr.o80.soulgame.MENU_TEXT_FONT
import fr.o80.soulgame.MENU_TEXT_SIZE
import fr.o80.soulgame.MENU_TITLE_FONT
import fr.o80.soulgame.MENU_TITLE_SIZE
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground

class LevelSelectorScene(
    private val sceneManager: SoulSceneManager
) : Scene {

    private lateinit var menu: Menu

    private val system = LevelSelectorSystem()

    override fun open(
        window: Window,
        services: Services,
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipeline,
        mouseMovePipeline: MouseMovePipeline
    ) {
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
                    font = resource(MENU_TEXT_FONT),
                    fontHeight = MENU_TEXT_SIZE
                ),
                titleResources = TextResources(
                    font = resource(MENU_TITLE_FONT),
                    fontHeight = MENU_TITLE_SIZE
                )
            )
            .withPipelines(
                mouseButtonPipeline,
                mouseMovePipeline
            )
            .andLayout {
                system.forEachLevel { levelName ->
                    button(levelName) {
                        sceneManager.openLevel(levelName)
                    }
                }
                button(services.messages["level_selector.back"]) {
                    sceneManager.openMain()
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
