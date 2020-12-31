package fr.o80.soulgame.scenes.main

import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.resource

class ButtonRenderer(buttonFontHeight: Float) {

    private val textRenderer: TextRenderer = TextRenderer(
        fontPath = resource("fonts/LaserCutRegular.ttf"),
        margin = 0f,
        fontHeight = buttonFontHeight
    )

    fun render(button: Button) {
        draw {
            pushed {
                translate(button.centerX - button.width/2, button.centerY - button.height/2, 0f)
                textRenderer.render(button.text)
            }
        }
    }

    fun init() {
        textRenderer.init()
    }

    fun getStringWidth(text: String): Float {
        return textRenderer.getStringWidth(text)
    }
}
