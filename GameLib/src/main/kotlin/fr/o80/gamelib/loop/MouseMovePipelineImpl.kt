package fr.o80.gamelib.loop

import org.lwjgl.glfw.GLFWCursorPosCallbackI

internal class MouseMovePipelineImpl(
    private val mouseButtonPipeline: MouseButtonPipelineImpl
) : MouseMovePipeline, GLFWCursorPosCallbackI {

    private val callbacks = mutableListOf<(Double, Double) -> Unit>()

    override fun invoke(window: Long, xpos: Double, ypos: Double) {
        mouseButtonPipeline.onMouseMove(xpos.toFloat(), ypos.toFloat())
        callbacks.forEach { it(xpos, ypos) }
    }

    override fun onMove(block: (Double, Double) -> Unit) {
        callbacks.add(block)
    }

    fun clear() {
        callbacks.clear()
    }

}