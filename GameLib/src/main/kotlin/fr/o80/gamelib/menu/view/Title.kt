package fr.o80.gamelib.menu.view

import fr.o80.gamelib.dsl.RectangleD

class Title(
    val text: String,
    override val horizontalMargin: Double = 0.0,
    override val verticalMargin: Double = 50.0,
) : MenuView {
    override var state: ViewState = ViewState.NORMAL
    override var bounds: RectangleD = RectangleD(.0, .0, .0, .0)
}
