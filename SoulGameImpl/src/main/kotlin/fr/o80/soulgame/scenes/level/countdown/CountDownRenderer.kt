package fr.o80.soulgame.scenes.level.countdown

import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.service.condition.LongValueOperand
import fr.o80.gamelib.service.goal.Goal
import fr.o80.gamelib.service.i18n.Messages
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.resourcePath
import fr.o80.soulgame.scenes.level.LevelState
import fr.o80.soulgame.scenes.level.PlayingState

class CountDownRenderer(
    private val window: Window,
    private val messages: Messages,
    private val goals: List<Goal>,
    font: String
) {

    private val initialTicks: Long = 120
    private var remainingTicks: Long = initialTicks
    private var remainingCount: Long = 3

    private val countDownRenderer: TextRenderer = TextRenderer(
        resourcePath(font),
        fontHeight = 40f
    )

    private val goalsRenderer: TextRenderer = TextRenderer(
        resourcePath(font),
        fontHeight = 30f
    )

    private lateinit var goalMessages: List<GoalMessage>

    fun open() {
        countDownRenderer.init()
        goalsRenderer.init()
        goalMessages =
            goals.map {
                val message =
                    messages["level.goal.${it.name.lowercase()}", it.getFixedValue()]
                GoalMessage(
                    message,
                    width = goalsRenderer.getStringWidth(message).toDouble(),
                    height = goalsRenderer.getStringHeight(message).toDouble()
                )
            }
    }

    fun close() {
        countDownRenderer.close()
        goalsRenderer.close()
    }

    fun update(levelState: LevelState) {
        remainingTicks--
        remainingCount = 1 + remainingTicks / (initialTicks / 3)
        if (remainingTicks <= 0) {
            levelState.playingState = PlayingState.PLAYING
        }
    }

    fun render() {

        draw {
            drawCountDown(remainingCount)
            drawGoals(goalMessages)
        }
    }

    private fun Draw.drawCountDown(remainingCount: Long) {
        val text = messages["level.wait_for_it", remainingCount]
        val countDownTranslateX = (window.width - countDownRenderer.getStringWidth(text)) / 2.0
        val countDownTranslateY = (window.height - countDownRenderer.getStringHeight(text)) / 2.0

        pushed {
            translate(countDownTranslateX, countDownTranslateY, .0)
            color(0f, 0f, 0f)
            countDownRenderer.render(text)

            translate(-1f, -1f, 0f)
            color(0.8f, 0f, 0f)
            countDownRenderer.render(text)
        }
    }

    private fun Draw.drawGoals(goalMessages: List<GoalMessage>) {
        val goalsTranslateY = (window.height * 3 / 4) - (goalMessages.sumOf { it.height } / 2)

        pushed {
            translate(.0, goalsTranslateY, .0)
            goalMessages.forEach { goal ->
                pushed {
                    color(0f, 0f, 0f)
                    translate((window.width - goal.width) / 2.0, 0.0, 0.0)
                    goalsRenderer.render(goal.message)

                    color(1f, 1f, 1f)
                    translate(-1.0, -1.0, .0)
                    goalsRenderer.render(goal.message)
                }
                translate(.0, goal.height, .0)
            }
        }
    }
}

private fun Goal.getFixedValue(): Long {
    return (this.condition.left as? LongValueOperand)?.value
           ?: (this.condition.right as? LongValueOperand)?.value
           ?: error("One of the operand of ${this.name} goal must be a number")
}
