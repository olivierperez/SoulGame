package fr.o80.soulgame.scenes.level.drawing

import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.service.i18n.Messages
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.scenes.level.Score
import fr.o80.soulgame.scenes.level.Timing

class HUD(
    private val textRenderer: TextRenderer,
    private val window: Window,
    private val messages: Messages
) {

    fun render(score: Score, timing: Timing) {
        draw {
            drawScore(score)
            drawTiming(timing)
        }
    }

    private fun Draw.drawScore(score: Score) {
        pushed {
            translate(16f, 11f, 0f)
            color(0f, 0f, 0f)
            textRenderer.render(messages["level.score", score.value])
        }
        pushed {
            translate(15f, 10f, 0f)
            color(0.8f, 0f, 0f)
            textRenderer.render(messages["level.score", score.value])
        }
    }

    private fun Draw.drawTiming(timing: Timing) {
        val totalSize = 200f
        val height = 20f
        val remainingSize = (totalSize * timing.remainingTicks / timing.initialTicks)
            .coerceAtLeast(0f)
            .coerceAtMost(totalSize)

        pushed {
            translate(window.width - totalSize - 64f, 20f, 0f)

            color(0.404f, 0.420f, 0.467f)
            quad(0f, 0f, totalSize, height)

            color(0.8f, 0f, 0f)
            quad(0f, 0f, remainingSize, height)

            color(0f, 0f, 0f)
            rect(0f, 0f, totalSize, height)
        }
    }
}
