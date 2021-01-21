package fr.o80.gamelib.menu.view

import fr.o80.gamelib.dsl.RectangleD

class Button(
    val text: String,
    private val click: () -> Unit,
    override val horizontalPadding: Double = 40.0,
    override val verticalPadding: Double = 10.0
) : Clickable, MenuView {

    override var state: ViewState = ViewState.NORMAL
    override var bounds: RectangleD = RectangleD(.0, .0, .0, .0)

    override fun onClick() {
        click()
    }
}
