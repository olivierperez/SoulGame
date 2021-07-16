package fr.o80.soulgame.scenes.levelselector

import fr.o80.gamelib.Scene
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipeline
import fr.o80.gamelib.loop.MouseMovePipeline
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.menu.Grid
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.service.Services
import fr.o80.soulgame.MENU_TEXT_FONT
import fr.o80.soulgame.MENU_TEXT_SIZE
import fr.o80.soulgame.MENU_TITLE_FONT
import fr.o80.soulgame.MENU_TITLE_SIZE
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.resourcePath
import fr.o80.soulgame.scenes.greenBackground

class LevelSelectorScene(
    private val sceneManager: SoulSceneManager
) : Scene {

    private lateinit var grid: Grid

    private val system = LevelSelectorSystem()

    override fun open(
        window: Window,
        services: Services,
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipeline,
        mouseMovePipeline: MouseMovePipeline
    ) {
        grid = Grid.GridBuilder()
            .of(
                top = .0,
                left = .0,
                right = window.width.toDouble(),
                bottom = window.height.toDouble()
            )
            .withDimens(
                cols = 7,
                horizontalSpacing = 40.0,
                verticalSpacing = 40.0,
            )
            .withPipelines(
                mouseButtonPipeline,
                mouseMovePipeline
            )
            .withResources(
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
            .andLayout {
                system.forEachLevel { level ->
                    button(level.code.toString(), enabled = level.selectable) {
                        sceneManager.openLevel(level.code)
                    }
                }
                mainButton(services.messages["level_selector.back"]) {
                    sceneManager.openMain()
                }
            }
            .build()
    }

    override fun close() {
        grid.close()
    }

    override suspend fun update() {
        grid.update()
    }

    override suspend fun render() {
        grid.render()
    }
}
