package fr.o80.soul.loop

import org.lwjgl.glfw.GLFWCursorPosCallbackI

class MouseMoveCallback : GLFWCursorPosCallbackI {

    private val callbacks = mutableListOf<(Double, Double) -> Unit>()

    override fun invoke(window: Long, xpos: Double, ypos: Double) {
        callbacks.forEach { it(xpos, ypos) }
    }

    fun add(block: (Double, Double) -> Unit) {
        callbacks.add(block)
    }

    fun reset() {
        callbacks.clear()
    }

}