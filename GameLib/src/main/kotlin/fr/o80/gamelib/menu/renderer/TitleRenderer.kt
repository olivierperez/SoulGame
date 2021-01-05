package fr.o80.gamelib.menu.renderer

import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.menu.view.MenuView
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.menu.view.Title
import fr.o80.gamelib.text.TextRenderer

class TitleRenderer(
    private val resources: TextResources
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
            pushed {
                translate(view.bounds.left, view.bounds.top, .0)
                textRenderer.render(view.text)
            }
        }
    }

    override fun getHeight(): Double {
        return resources.fontHeight.toDouble()
    }

    override fun getWidth(view: MenuView): Double {
        view as? Title ?: throw IllegalStateException("The given view must be checked with \"canRender()\" method")
        return textRenderer.getStringWidth(view.text).toDouble()
    }

    override fun init() {
        textRenderer.init()
    }

    override fun close() {
        textRenderer.close()
    }
}
