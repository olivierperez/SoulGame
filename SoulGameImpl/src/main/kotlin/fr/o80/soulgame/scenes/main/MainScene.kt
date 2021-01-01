package fr.o80.soulgame.scenes.main

import fr.o80.gamelib.Scene
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Dimension
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipelineImpl
import fr.o80.gamelib.loop.MouseMovePipelineImpl
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground
import org.lwjgl.glfw.GLFW
import kotlin.math.abs

class MainScene(
    private val sceneManager: SoulSceneManager
) : Scene {

    private val title: String = "Soul Game"
    private val titleFontHeight: Float = 99f
    private var titleWidth: Float = -1f

    private val startText: String = "Start"
    private val quitText: String = "Quit"
    private val buttonFontHeight: Float = 50f

    private var centerX: Float = -1f
    private var centerY: Float = -1f

    private lateinit var startButton: Button
    private lateinit var quitButton: Button
    private lateinit var buttons: List<Button>

    private val titleTextRenderer: TextRenderer = TextRenderer(
        fontPath = resource("fonts/LaserCutRegular.ttf"),
        margin = 0f,
        fontHeight = titleFontHeight
    )

    private val buttonRenderer = ButtonRenderer(buttonFontHeight)

    // TODO Mouse cursor http://forum.lwjgl.org/index.php?topic=5757.0
    // TODO Ou https://gamedev.stackexchange.com/a/124395

    override fun open(
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipelineImpl,
        mouseMovePipeline: MouseMovePipelineImpl,
        dimension: Dimension
    ) {
        mouseButtonPipeline.onButton(GLFW.GLFW_MOUSE_BUTTON_LEFT, GLFW.GLFW_RELEASE) { x, y ->
            handleClick(x, y)
        }
        mouseMovePipeline.onMove { x, y ->
            handleMove(x, y)
        }

        // Load resources
        titleTextRenderer.init()
        buttonRenderer.init()
        titleWidth = titleTextRenderer.getStringWidth(title)

        centerX = dimension.width / 2f
        centerY = dimension.height / 2f

        val startWidth = buttonRenderer.getStringWidth(startText)
        startButton = Button(
            startText,
            centerX = centerX,
            centerY = centerY - buttonFontHeight - buttonFontHeight / 2 + titleFontHeight,
            width = startWidth + 80f,
            height = buttonFontHeight + 20f
        ) {
            sceneManager.openLevel("level_1")
        }

        val quitWidth = buttonRenderer.getStringWidth(quitText)
        quitButton = Button(
            quitText,
            centerX = centerX,
            centerY = centerY + buttonFontHeight / 2 + titleFontHeight,
            width = quitWidth + 80f,
            height = buttonFontHeight + 20f
        ) {
            sceneManager.quit()
        }

        buttons = listOf(startButton, quitButton)
    }

    private fun handleClick(x: Float, y: Float) {
        buttons.forEach { button ->
            if (abs(x - button.centerX) < button.width / 2 && abs(y - button.centerY) < button.height / 2) {
                button.onClick()
            }
        }
    }

    private fun handleMove(x: Double, y: Double) {
        buttons.forEach { button ->
            if (abs(x - button.centerX) < button.width / 2 && abs(y - button.centerY) < button.height / 2) {
                button.state = Button.State.HOVER
            } else {
                button.state = Button.State.NORMAL
            }
        }
    }

    override fun close() {
        // Unload resources
    }

    override suspend fun update() {

    }

    override suspend fun render() {
        draw {
            clear(greenBackground)

            color(1f, 1f, 1f)
            pushed {
                translate(centerX - titleWidth / 2, titleFontHeight / 2, 0f)
                titleTextRenderer.render(title)
            }

            buttonRenderer.render(startButton)
            buttonRenderer.render(quitButton)
        }
    }
}