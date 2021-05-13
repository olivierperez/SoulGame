package fr.o80.soulgame.scenes.gameover

import fr.o80.gamelib.Scene
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipeline
import fr.o80.gamelib.loop.MouseMovePipeline
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.menu.Menu
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.service.Services
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.data.InFileScoreRepository
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground
import org.lwjgl.glfw.GLFW

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
        keyPipeline.onKey(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_RELEASE) {
            sceneManager.openLevel(info.levelName)
        }
        keyPipeline.onKey(GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_RELEASE) {
            sceneManager.quit()
        }

        system = GameOverSystem(info, InFileScoreRepository())
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
                    font = resource("fonts/LaserCutRegular.ttf"),
                    fontHeight = 30f
                ),
                titleResources = TextResources(
                    font = resource("fonts/LaserCutRegular.ttf"),
                    fontHeight = 80f
                )
            )
            .withPipelines(
                mouseButtonPipeline,
                mouseMovePipeline
            )
            .andLayout {
                title("Game Over")
                text("Level: ${info.levelName}")
                text { "SCORE: ${state.score}" }
                text { "Best: ${state.bestScore}" }
                button("Restart") {
                    sceneManager.openLevel(info.levelName)
                }
                button("Select level") {
                    sceneManager.openLevelSelector()
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
        system.update(state)
        menu.update()
    }

    override suspend fun render() {
        menu.render()
    }

}