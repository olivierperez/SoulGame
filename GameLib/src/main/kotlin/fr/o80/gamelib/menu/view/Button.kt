package fr.o80.gamelib.menu.view

import fr.o80.gamelib.dsl.RectangleD

class Button(
    val text: String,
    enabled: Boolean,
    private val click: () -> Unit,
    override val horizontalPadding: Double = 40.0,
    override val verticalPadding: Double = 15.0
) : Clickable, MenuView {

    override var state: ViewState = if (enabled) ViewState.NORMAL else ViewState.DISABLED
    override var bounds: RectangleD = RectangleD(.0, .0, .0, .0)

    override fun onClick() {
        click()
    }

    override fun onHover() {
        if (state != ViewState.DISABLED) {
            state = ViewState.HOVER
        }
    }

    override fun onLeave() {
        if (state != ViewState.DISABLED) {
            state = ViewState.NORMAL
        }
    }
}
