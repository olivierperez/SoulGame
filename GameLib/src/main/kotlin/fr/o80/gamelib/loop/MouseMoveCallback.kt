package fr.o80.gamelib.loop

import org.lwjgl.glfw.GLFWCursorPosCallbackI

class MouseMoveCallback(
    private val mouseButtonPipeline: MouseButtonPipelineImpl
) : GLFWCursorPosCallbackI {

    private val callbacks = mutableListOf<(Double, Double) -> Unit>()

    override fun invoke(window: Long, xpos: Double, ypos: Double) {
        mouseButtonPipeline.onMouseMove(xpos.toFloat(), ypos.toFloat())
        callbacks.forEach { it(xpos, ypos) }
    }

    fun add(block: (Double, Double) -> Unit) {
        callbacks.add(block)
    }

    fun reset() {
        callbacks.clear()
    }

}