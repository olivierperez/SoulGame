package fr.o80.gamelib.menu.view

import fr.o80.gamelib.dsl.RectangleD

class Button(
    val text: String,
    private val click: () -> Unit,
) : Clickable, MenuView {
    override var state: ViewState = ViewState.NORMAL
    override var bounds: RectangleD = RectangleD(.0,.0,.0,.0)

    override fun onClick() {
        click()
    }
}
