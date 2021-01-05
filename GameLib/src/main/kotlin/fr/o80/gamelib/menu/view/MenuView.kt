package fr.o80.gamelib.menu.view

import fr.o80.gamelib.dsl.RectangleD

interface MenuView {
    var state: ViewState
    var bounds: RectangleD
}

enum class ViewState {
    NORMAL, HOVER
}
