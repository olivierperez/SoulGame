package fr.o80.gamelib.loop

import org.lwjgl.glfw.GLFWMouseButtonCallbackI

internal class MouseButtonPipelineImpl : MouseButtonPipeline, GLFWMouseButtonCallbackI {

    private val callbacks = mutableListOf<Triple<Int, Int, (Float, Float) -> Unit>>()

    private var mouseX: Float = -1f
    private var mouseY: Float = -1f

    override fun invoke(window: Long, button: Int, action: Int, mods: Int) {
        callbacks.filter { (k, a, _) -> k == button && a == action }
            .forEach { it.third(mouseX, mouseY) }
    }

    override fun onButton(button: Int, action: Int, block: (x: Float, y: Float) -> Unit) {
        callbacks.add(Triple(button, action, block))
    }

    fun onMouseMove(x: Float, y: Float) {
        mouseX = x
        mouseY = y
    }

    fun clear() {
        callbacks.clear()
    }

}