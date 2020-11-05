package fr.o80.gamelib

import fr.o80.gamelib.loop.Dimension
import fr.o80.gamelib.loop.KeyPipeline

interface Scene {
    fun open(keyPipeline: KeyPipeline, dimension: Dimension)
    fun close()
    suspend fun update()
    suspend fun render()
}