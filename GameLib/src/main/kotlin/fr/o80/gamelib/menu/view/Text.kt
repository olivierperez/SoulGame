package fr.o80.gamelib.menu.view

import fr.o80.gamelib.dsl.RectangleD

class Text(
    val text: String
): MenuView {
    override var state: ViewState = ViewState.NORMAL
    override var bounds: RectangleD = RectangleD(.0, .0, .0, .0)
}
