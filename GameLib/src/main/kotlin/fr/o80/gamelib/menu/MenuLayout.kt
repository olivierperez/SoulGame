package fr.o80.gamelib.menu

import fr.o80.gamelib.dsl.RectangleD
import fr.o80.gamelib.dsl.Vertex2d
import fr.o80.gamelib.menu.renderer.ViewRenderer
import fr.o80.gamelib.menu.view.Button
import fr.o80.gamelib.menu.view.MenuView
import fr.o80.gamelib.menu.view.Title

class MenuLayout(
    private val verticalSpacing: Double
) {

    val views: MutableList<MenuView> = mutableListOf()

    fun title(text: String) {
        views += Title(text)
    }

    fun button(text: String, onClick: () -> Unit) {
        views += Button(text, onClick)
    }

    fun computeBounds(bounds: RectangleD, renderers: List<ViewRenderer>) {
        val (centerX, centerY) = bounds.center()

        val dimensions = views.map { view ->
            val renderer = renderers.first { it.canRender(view) }
            Vertex2d(
                x = renderer.getWidth(view),
                y = renderer.getHeight()
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
