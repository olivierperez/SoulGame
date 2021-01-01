package fr.o80.soulgame.scenes.main

import fr.o80.gamelib.drawing.Cursor
import fr.o80.soulgame.resourceFile
import org.lwjgl.glfw.GLFW

class MainResources(
    private val window: Long
) {

    private lateinit var cursor: Cursor

    fun open() {
        cursor = Cursor(resourceFile("sprites/pointer.png"), 16, 16)
        GLFW.glfwSetCursor(window, cursor.id)
    }

}
