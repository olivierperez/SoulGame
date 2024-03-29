package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.service.i18n.Messages
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.resourcePath
import fr.o80.soulgame.scenes.level.level.LevelSettings

class HUD(
    settings: LevelSettings,
    private val window: Window,
    private val messages: Messages
) {

    private val textRenderer: TextRenderer = TextRenderer(resourcePath(settings.font))

    fun open() {
        textRenderer.init()
    }

    fun close() {
        textRenderer.close()
    }

    fun render(score: Score, mana: Mana) {
        draw {
            drawScore(score)
            drawTiming(mana)
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

    private fun Draw.drawTiming(mana: Mana) {
        val totalSize = 200f
        val height = 20f
        val remainingSize = (totalSize * mana.remaining / mana.max)
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
