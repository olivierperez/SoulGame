package fr.o80.gamelib.menu.renderer

import fr.o80.gamelib.GG
import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.menu.view.Button
import fr.o80.gamelib.menu.view.MenuView
import fr.o80.gamelib.menu.view.ViewState
import fr.o80.gamelib.text.TextRenderer

class ButtonRenderer(
    resources: TextResources,
    private val drawDebug: Boolean = false
) : ViewRenderer {

    private val textRenderer: TextRenderer = TextRenderer(
        fontPath = resources.font,
        margin = 0f,
        fontHeight = resources.fontHeight
    )

    override fun canRender(view: MenuView): Boolean {
        return view is Button
    }

    override fun render(view: MenuView) {
        view as? Button ?: throw IllegalStateException("The given view must be checked with \"canRender()\" method")
        draw {
            if (drawDebug) drawDebug(view)
            drawBackground(view)
            drawText(view)
        }
    }

    override fun getHeight(view: MenuView): Double {
        return textRenderer.getStringHeight() + 2 * (view.verticalMargin + view.verticalPadding)
    }

    override fun getWidth(view: MenuView): Double {
        view as? Button ?: throw IllegalStateException("The given view must be checked with \"canRender()\" method")
        return textRenderer.getStringWidth(view.text) + 2 * (view.horizontalMargin + view.horizontalPadding)
    }

    override fun init() {
        textRenderer.init()
    }

    override fun close() {
        textRenderer.close()
    }

    private fun Draw.drawDebug(button: Button) {
        color(.8f, 0f, 0f)
        rect(
            button.bounds.left,
            button.bounds.top,
            button.bounds.right,
            button.bounds.bottom
        )
        color(0f, 0f, .8f)
        rect(
            button.bounds.left + button.horizontalMargin,
            button.bounds.top + button.verticalMargin,
            button.bounds.right - button.horizontalMargin,
            button.bounds.bottom - button.verticalMargin
        )
        color(0f, 0f, 0f)
        rect(
            button.bounds.left + button.horizontalMargin + button.horizontalPadding,
            button.bounds.top + button.verticalMargin + button.verticalPadding,
            button.bounds.right - button.horizontalMargin - +button.horizontalPadding,
            button.bounds.bottom - button.verticalMargin - button.verticalPadding
        )
    }

    private fun Draw.drawText(button: Button) {
        pushed {
            translate(
                button.bounds.left + button.horizontalMargin + button.horizontalPadding,
                button.bounds.top + button.verticalMargin + button.verticalPadding,
                .0
            )
            textRenderer.render(button.text)
        }
    }

    private fun Draw.drawBackground(button: Button) {
        if (button.state == ViewState.HOVER) {
            GG.glEnable(GG.GL_BLEND)
            GG.glBlendFunc(GG.GL_SRC_ALPHA, GG.GL_ONE_MINUS_SRC_ALPHA)
            color(1f, 1f, 1f, 0.1f)
            quad(
                button.bounds.left + button.horizontalMargin,
                button.bounds.top + button.verticalMargin,
                button.bounds.right - button.horizontalMargin,
                button.bounds.bottom - button.verticalMargin
            )
            GG.glDisable(GG.GL_BLEND)
        }

        color(1f, 1f, 1f)
        lineWidth(2f)
        rect(
            button.bounds.left,
            button.bounds.top,
            button.bounds.right,
            button.bounds.bottom
        )
    }

}
