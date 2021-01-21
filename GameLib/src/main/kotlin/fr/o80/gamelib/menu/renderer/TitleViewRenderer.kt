package fr.o80.gamelib.menu.renderer

import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.menu.view.MenuView
import fr.o80.gamelib.menu.view.Title
import fr.o80.gamelib.text.TextRenderer

class TitleViewRenderer(
    resources: TextResources,
    private val drawDebug: Boolean = false
) : ViewRenderer {

    private val textRenderer: TextRenderer = TextRenderer(
        fontPath = resources.font,
        margin = 0f,
        fontHeight = resources.fontHeight
    )

    override fun canRender(view: MenuView): Boolean {
        return view is Title
    }

    override fun render(view: MenuView) {
        view as? Title ?: throw IllegalStateException("The given view must be checked with \"canRender()\" method")
        draw {
            if (drawDebug) drawDebug(view)
            drawText(view)
        }
    }

    override fun getHeight(view: MenuView): Double {
        return textRenderer.getStringHeight() + 2 * (view.verticalMargin + view.verticalPadding)
    }

    override fun getWidth(view: MenuView): Double {
        view as? Title ?: throw IllegalStateException("The given view must be checked with \"canRender()\" method")
        return textRenderer.getStringWidth(view.text).toDouble() + 2 * (view.horizontalMargin + view.horizontalPadding)
    }

    override fun init() {
        textRenderer.init()
    }

    override fun close() {
        textRenderer.close()
    }

    private fun Draw.drawDebug(title: Title) {
        color(.8f, 0f, 0f)
        rect(
            title.bounds.left,
            title.bounds.top,
            title.bounds.right,
            title.bounds.bottom
        )
        color(0f, 0f, .8f)
        rect(
            title.bounds.left + title.horizontalMargin,
            title.bounds.top + title.verticalMargin,
            title.bounds.right - title.horizontalMargin,
            title.bounds.bottom - title.verticalMargin
        )
        color(0f, 0f, 0f)
        rect(
            title.bounds.left + title.horizontalMargin + title.horizontalPadding,
            title.bounds.top + title.verticalMargin + title.verticalPadding,
            title.bounds.right - title.horizontalMargin - title.horizontalPadding,
            title.bounds.bottom - title.verticalMargin - title.verticalPadding
        )
    }

    private fun Draw.drawText(title: Title) {
        pushed {
            color(1f, 1f, 1f)
            translate(
                title.bounds.left + title.horizontalMargin + title.horizontalPadding,
                title.bounds.top + title.verticalMargin + title.verticalPadding,
                .0
            )
            textRenderer.render(title.text)
        }
    }
}
