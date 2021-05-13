package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.resource

class CountDownRenderer(
    private val window: Window
) {

    private val initialTicks: Long = 80
    private var remainingTicks: Long = initialTicks
    private var remainingCount: Long = 3

    private val textRenderer: TextRenderer = TextRenderer(
        resource("fonts/LaserCutRegular.ttf"),
        fontHeight = 60f
    )

    fun open() {
        textRenderer.init()
    }

    fun close() {
        textRenderer.close()
    }

    fun update(levelState: LevelState) {
        remainingTicks--
        remainingCount = 1 + remainingTicks / (initialTicks / 3)
        if (remainingTicks <= 0) {
            levelState.playingState = PlayingState.PLAYING
        }
    }

    fun render() {
        val text = "Wait for it $remainingCount"
        val translateX = (window.width - textRenderer.getStringWidth(text)) / 2.0
        val translateY = (window.height - textRenderer.getStringHeight()) / 2.0

        draw {
            pushed {
                translate(translateX,translateY,.0)
                color(0f, 0f, 0f)
                textRenderer.render(text)

                translate(-1f, -1f, 0f)
                color(0.8f, 0f, 0f)
                textRenderer.render(text)
            }
        }
    }
}
