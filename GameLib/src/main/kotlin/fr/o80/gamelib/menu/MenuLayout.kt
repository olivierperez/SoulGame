package fr.o80.gamelib.menu

import fr.o80.gamelib.dsl.RectangleD
import fr.o80.gamelib.dsl.Vertex2d
import fr.o80.gamelib.menu.renderer.ViewRenderer
import fr.o80.gamelib.menu.view.Button
import fr.o80.gamelib.menu.view.MenuView
import fr.o80.gamelib.menu.view.Text
import fr.o80.gamelib.menu.view.Title

class MenuLayout(
    private val verticalSpacing: Double
) {

    val views: MutableList<MenuView> = mutableListOf()

    fun button(text: String, onClick: () -> Unit) {
        views += Button(text, true, onClick)
    }

    fun text(text: String) {
        views += Text { text }
    }

    fun text(text: () -> String) {
        views += Text(text)
    }

    fun title(text: String, verticalMargin: Double = .0, horizontalMargin: Double = .0) {
        views += Title(text, verticalMargin = verticalMargin, horizontalMargin = horizontalMargin)
    }

    fun computeBounds(bounds: RectangleD, renderers: List<ViewRenderer>) {
        val (centerX, centerY) = bounds.center()

        val dimensions = views.map { view ->
            val renderer = renderers.firstOrNull { it.canRender(view) }
                           ?: throw IllegalStateException("No ViewRenderer was configured for ${view::class.java.simpleName}")
            Vertex2d(
                x = renderer.getWidth(view),
                y = renderer.getHeight(view)
            )
        }
        val totalHeight = dimensions.map { it.y }.sum() + verticalSpacing * (views.size - 1)

        var top = centerY - totalHeight / 2
        views.forEachIndexed { index, view ->
            val dimension = dimensions[index]

            val left = centerX - dimension.x / 2
            val right = centerX + dimension.x / 2
            val bottom = top + dimension.y

            view.bounds = RectangleD(top, right, bottom, left)
            top = bottom + verticalSpacing
        }
    }

}
