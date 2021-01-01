package fr.o80.soulgame.scenes.main

class Button(
    val text: String,
    val centerX: Float,
    val centerY: Float,
    val width: Float,
    val height: Float,
    var state: State = State.NORMAL,
    val onClick: () -> Unit
) {
    enum class State {
        NORMAL, HOVER
    }
}
