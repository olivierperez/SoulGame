package fr.o80.soulgame.scenes.gameover

import fr.o80.gamelib.Scene
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipelineImpl
import fr.o80.gamelib.loop.MouseMovePipelineImpl
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground
import org.lwjgl.glfw.GLFW

class GameOverScene(
    private val sceneManager: SoulSceneManager,
    private val score: Long
) : Scene {

    private val fontHeight: Float = 50f

    private val textRenderer: TextRenderer = TextRenderer(
        fontPath = resource("fonts/LaserCutRegular.ttf"),
        margin = 0f,
        fontHeight = fontHeight
    )

    private var centerX: Int = -1
    private var centerY: Int = -1

    override fun open(
        window: Window,
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipelineImpl,
        mouseMovePipeline: MouseMovePipelineImpl
    ) {
        keyPipeline.onKey(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_RELEASE) {
            sceneManager.openLevel("level_1")
        }
        keyPipeline.onKey(GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_RELEASE) {
            sceneManager.quit()
        }

        centerX = window.width / 2
        centerY = window.height / 2

        textRenderer.init()
    }

    override fun close() {
    }

    override suspend fun update() {
    }

    override suspend fun render() {
        draw {
            clear(greenBackground)
            color(0f, 0f, 0f)
            val text = "SCORE: $score"

            pushed {
                translate(
                    centerX.toFloat() - textRenderer.getStringWidth(text) / 2,
                    centerY.toFloat() - fontHeight / 2 - textRenderer.getStringHeight() / 2,
                    0f
                )
                textRenderer.render(text)
            }
        }
    }

}