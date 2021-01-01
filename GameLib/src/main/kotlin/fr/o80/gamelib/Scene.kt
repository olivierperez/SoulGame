package fr.o80.gamelib

import fr.o80.gamelib.loop.Dimension
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipelineImpl
import fr.o80.gamelib.loop.MouseMovePipelineImpl

interface Scene {
    fun open(
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipelineImpl,
        mouseMovePipeline: MouseMovePipelineImpl,
        dimension: Dimension
    )
    fun close()
    suspend fun update()
    suspend fun render()
}