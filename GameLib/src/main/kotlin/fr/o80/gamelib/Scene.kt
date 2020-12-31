package fr.o80.gamelib

import fr.o80.gamelib.loop.Dimension
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipelineImpl

interface Scene {
    fun open(keyPipeline: KeyPipeline, mouseButtonPipeline: MouseButtonPipelineImpl, dimension: Dimension)
    fun close()
    suspend fun update()
    suspend fun render()
}