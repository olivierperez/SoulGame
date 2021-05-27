package fr.o80.gamelib.menu.renderer

import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.menu.view.MenuView
import fr.o80.gamelib.menu.view.Text
import fr.o80.gamelib.text.TextRenderer

class TextViewRenderer(
    resources: TextResources,
    private val drawDebug: Boolean = false
): ViewRenderer {

    private val textRenderer: TextRenderer = TextRenderer(
        fontPath = resources.font,
        margin = 0f,
        fontHeight = resources.fontHeight
    )

    override fun canRender(view: MenuView): Boolean {
        return view is Text
    }

    override fun render(view: MenuView) {
        view as? Text ?: throw IllegalStateException("The given view must be checked with \"canRender()\" method")
        draw {
            if (drawDebug) drawDebug(view)
            drawText(view)
        }
    }

    private fun Draw.drawText(view: Text) {
        pushed {
            translate(
                view.bounds.left + view.horizontalMargin + view.horizontalPadding,
                view.bounds.top + view.verticalMargin + view.verticalPadding,
                .0
            )
            textRenderer.render(view.text())
        }
    }

    override fun init() {
        textRenderer.init()
    }

    override fun close() {
        textRenderer.close()
    }

    override fun getHeight(view: MenuView): Double {
        view as? Text ?: throw IllegalStateException("The given view must be checked with \"canRender()\" method")
        return textRenderer.getStringHeight(view.text()) + 2 * (view.verticalMargin + view.verticalPadding)
    }

    // TODO Ekaliroots - Abstraire ça dans un abstract, façon method-template
    override fun getWidth(view: MenuView): Double {
        view as? Text ?: throw IllegalStateException("The given view must be checked with \"canRender()\" method")
        return textRenderer.getStringWidth(view.text()) + 2 * (view.horizontalMargin + view.horizontalPadding)
    }

}
