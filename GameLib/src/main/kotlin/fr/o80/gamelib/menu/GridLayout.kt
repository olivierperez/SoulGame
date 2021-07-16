package fr.o80.gamelib.menu

import fr.o80.gamelib.dsl.RectangleD
import fr.o80.gamelib.menu.renderer.ButtonViewRenderer
import fr.o80.gamelib.menu.view.Button
import fr.o80.gamelib.menu.view.MenuView

class GridLayout {

    val views: List<MenuView>
        get() = mainButton?.let { _views + it } ?: _views

    private val _views: MutableList<MenuView> = mutableListOf()

    private var mainButton: Button? = null

    fun button(text: String, enabled: Boolean, onClick: () -> Unit) {
        _views += Button(text, enabled, onClick)
    }

    fun mainButton(text: String, onClick: () -> Unit) {
        mainButton = Button(text, true, onClick)
    }

    fun computeBounds(
        bounds: RectangleD,
        cols: Int,
        horizontalSpacing: Double,
        verticalSpacing: Double,
        renderer: ButtonViewRenderer
    ) {
        val buttonWidth = ((bounds.width - horizontalSpacing) / cols) - horizontalSpacing
        val buttonHeight = renderer.getHeight(_views.first())

        _views.forEachIndexed { index, view ->
            val xIndex = index % cols
            val yIndex = index / cols

            val top = yIndex * (buttonHeight + verticalSpacing) + verticalSpacing
            val left = xIndex * (buttonWidth + horizontalSpacing) + horizontalSpacing
            val right = left + buttonWidth
            val bottom = top + buttonHeight

            view.bounds = RectangleD(
                top = top,
                right = right,
                bottom = bottom,
                left = left
            )
        }

        mainButton?.apply {
            val mainButtonWidth = renderer.getWidth(this)
            val center = bounds.left + bounds.width / 2
            val bottom = bounds.bottom - verticalSpacing
            val top = bottom - buttonHeight
            val left = center - mainButtonWidth / 2
            val right = center + mainButtonWidth / 2

            this.bounds = RectangleD(
                top = top,
                right = right,
                bottom = bottom,
                left = left
            )
        }
    }

}
