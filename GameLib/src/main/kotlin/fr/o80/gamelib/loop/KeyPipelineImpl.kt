package fr.o80.gamelib.loop

import org.lwjgl.glfw.GLFWKeyCallbackI

class KeyPipelineImpl : KeyPipeline, GLFWKeyCallbackI {

    private val callbacks = mutableListOf<Triple<Int, Int, () -> Unit>>()

    fun clear() {
        callbacks.clear()
    }

    override fun onKey(key: Int, action: Int, block: () -> Unit) {
        callbacks.add(Triple(key, action, block))
    }

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        callbacks.filter { (k, a, _) -> k == key && a == action }
            .forEach { it.third() }
    }

}