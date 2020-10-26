package fr.o80.gamelib.loop

import org.lwjgl.glfw.GLFWMouseButtonCallbackI

class MouseButtonCallback : GLFWMouseButtonCallbackI {

    private val callbacks = mutableListOf<Triple<Int, Int, () -> Unit>>()

    override fun invoke(window: Long, button: Int, action: Int, mods: Int) {
        callbacks.filter { (k, a, _) -> k == button && a == action }
            .forEach { it.third() }
    }

    fun add(button: Int, action: Int, block: () -> Unit) {
        callbacks.add(Triple(button, action, block))
    }

    fun reset() {
        callbacks.clear()
    }

}