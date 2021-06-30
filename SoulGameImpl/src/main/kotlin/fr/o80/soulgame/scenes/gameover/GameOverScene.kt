package fr.o80.soulgame.scenes.gameover

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
import fr.o80.soulgame.resourcePath
import fr.o80.soulgame.scenes.greenBackground

class GameOverScene(
    private val sceneManager: SoulSceneManager,
    private val info: GameOverInfo
) : Scene {

    private lateinit var system: GameOverSystem
    private lateinit var state: GameOverState
    private lateinit var menu: Menu

    override fun open(
        window: Window,
        services: Services,
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipeline,
        mouseMovePipeline: MouseMovePipeline
    ) {
        system = GameOverSystem(info).also { it.save() }
        state = GameOverState()

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
                    font = resourcePath(MENU_TEXT_FONT),
                    fontHeight = MENU_TEXT_SIZE
                ),
                titleResources = TextResources(
                    font = resourcePath(MENU_TITLE_FONT),
                    fontHeight = MENU_TITLE_SIZE
                )
            )
            .withPipelines(
                mouseButtonPipeline,
                mouseMovePipeline
            )
            .andLayout {
                title(services.messages["gameover.title"])
                text(services.messages["gameover.level", info.levelSettings.name])
                text { services.messages["gameover.score", state.score] }
                text { services.messages["gameover.best", state.bestScore] }
                button(services.messages["gameover.restart"]) {
                    sceneManager.openLevel(info.levelSettings.code)
                }
                button(services.messages["gameover.select_level"]) {
                    sceneManager.openLevelSelector()
                }
                button(services.messages["gameover.quit"]) {
                    sceneManager.quit()
                }
            }
            .build()
    }

    override fun close() {
        menu.close()
    }

    override suspend fun update() {
        system.update(state)
        menu.update()
    }

    override suspend fun render() {
        menu.render()
    }

}