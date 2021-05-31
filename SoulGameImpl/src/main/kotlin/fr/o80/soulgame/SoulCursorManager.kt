package fr.o80.soulgame

import fr.o80.gamelib.drawing.Cursor
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.service.cursor.CursorManager
import org.lwjgl.glfw.GLFW

class SoulCursorManager(private val window: Window) : CursorManager {

    private var pointer: Cursor = Cursor(resourceFile("pointer.png"), 16, 16)

    override fun setCursor(type: Int) {
        when (type) {
            POINTER -> GLFW.glfwSetCursor(window.id, pointer.id)
        }
    }

    companion object {
        const val POINTER = 0
    }

}
