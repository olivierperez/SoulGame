package fr.o80.gamelib

import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipelineImpl
import fr.o80.gamelib.loop.MouseMovePipelineImpl

interface Scene {
    fun open(
        window: Window,
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipelineImpl,
        mouseMovePipeline: MouseMovePipelineImpl
    )
    fun close()
    suspend fun update()
    suspend fun render()
}