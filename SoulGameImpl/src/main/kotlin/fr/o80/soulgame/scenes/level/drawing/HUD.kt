package fr.o80.soulgame.scenes.level.drawing

import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.scenes.level.Score

class HUD(
    private val score: Score,
    private val textRenderer: TextRenderer
) {

    fun render() {
        draw {
            pushed {
                color(0f, 0f, 0f)
                translate(16f, 11f, 0f)
                textRenderer.render("SCORE ${score.value}")
            }
            pushed {
                color(0.8f, 0f, 0f)
                translate(15f, 10f, 0f)
                textRenderer.render("SCORE ${score.value}")
            }
        }
    }
}
