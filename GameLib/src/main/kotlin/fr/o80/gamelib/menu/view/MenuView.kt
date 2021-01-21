package fr.o80.gamelib.menu.view

import fr.o80.gamelib.dsl.RectangleD

interface MenuView {
    var state: ViewState
    var bounds: RectangleD

    val horizontalMargin: Double get() = 0.0
    val verticalMargin: Double get() = 0.0
    val horizontalPadding: Double get() = 0.0
    val verticalPadding: Double get() = 0.0
}

enum class ViewState {
    NORMAL, HOVER
}
