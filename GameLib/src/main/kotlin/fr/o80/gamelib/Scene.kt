package fr.o80.gamelib

import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipeline
import fr.o80.gamelib.loop.MouseMovePipeline
import fr.o80.gamelib.loop.Window

interface Scene {
    fun open(
        window: Window,
        cursorManager: CursorManager,
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipeline,
        mouseMovePipeline: MouseMovePipeline
    )
    fun close()
    suspend fun update()
    suspend fun render()
}