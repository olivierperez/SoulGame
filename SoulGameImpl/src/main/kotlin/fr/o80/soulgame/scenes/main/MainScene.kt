package fr.o80.soulgame.scenes.main

import fr.o80.gamelib.Scene
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Dimension
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground
import org.lwjgl.glfw.GLFW

class MainScene(
    private val sceneManager: SoulSceneManager
) : Scene {

    private val title: String = "Soul Game"
    private val titleFontHeight: Float = 99f
    private var titleWidth: Float = -1f

    private val startText: String = "Start"
    private val quitText: String = "Quit"
    private val buttonFontHeight: Float = 50f

    private var centerX: Int = -1
    private var centerY: Int = -1

    private lateinit var startButton: Button
    private lateinit var quitButton: Button

    private val titleTextRenderer: TextRenderer = TextRenderer(
        fontPath = resource("fonts/LaserCutRegular.ttf"),
        margin = 0f,
        fontHeight = titleFontHeight
    )

    private val buttonRenderer = ButtonRenderer(buttonFontHeight)

    override fun open(keyPipeline: KeyPipeline, dimension: Dimension) {
        keyPipeline.onKey(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_RELEASE) {
            sceneManager.openLevel("level_1")
        }
        keyPipeline.onKey(GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_RELEASE) {
            sceneManager.quit()
        }

        // Load resources
        titleTextRenderer.init()
        buttonRenderer.init()
        titleWidth = titleTextRenderer.getStringWidth(title)

        centerX = dimension.width / 2
        centerY = dimension.height / 2

        val startWidth = buttonRenderer.getStringWidth(startText)
        startButton = Button(
            startText,
            centerX = centerX - startWidth / 2,
            centerY = centerY - buttonFontHeight - buttonFontHeight / 2,
            width = startWidth,
            height = buttonFontHeight
        )

        val quitWidth = buttonRenderer.getStringWidth(quitText)
        quitButton = Button(
            quitText,
            centerX = centerX - quitWidth / 2,
            centerY = centerY.toFloat() + buttonFontHeight / 2,
            width = startWidth,
            height = buttonFontHeight
        )
    }

    override fun close() {
        // Unload resources
    }

    override suspend fun update() {

    }

    override suspend fun render() {
        draw {
            clear(greenBackground)

            pushed {
                translate(centerX - titleWidth / 2, titleFontHeight / 2, 0f)
                titleTextRenderer.render(title)
            }

            buttonRenderer.render(startButton)
            buttonRenderer.render(quitButton)
        }
    }
}