package fr.o80.soulgame.scenes.main

import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.lightGreenBackground

class ButtonRenderer(buttonFontHeight: Float) {

    private val textRenderer: TextRenderer = TextRenderer(
        fontPath = resource("fonts/LaserCutRegular.ttf"),
        margin = 0f,
        fontHeight = buttonFontHeight
    )

    fun render(button: Button) {
        draw {
            drawBackground(button)
            drawText(button)
        }
    }

    private fun Draw.drawText(button: Button) {
        val textWidth = textRenderer.getStringWidth(button.text)
        pushed {
            translate(button.centerX - textWidth / 2, button.centerY - button.height / 2, 0f)
            textRenderer.render(button.text)
        }
    }

    private fun Draw.drawBackground(button: Button) {
        if (button.state == Button.State.HOVER) {
            color(lightGreenBackground)
            quad(
                button.centerX - button.width / 2,
                button.centerY - button.height / 2,
                button.centerX + button.width / 2,
                button.centerY + button.height / 2
            )
        }

        color(1f, 1f, 1f)
        lineWidth(2f)
        rect(
            button.centerX - button.width / 2,
            button.centerY - button.height / 2,
            button.centerX + button.width / 2,
            button.centerY + button.height / 2
        )
    }

    fun init() {
        textRenderer.init()
    }

    fun getStringWidth(text: String): Float {
        return textRenderer.getStringWidth(text)
    }
}
