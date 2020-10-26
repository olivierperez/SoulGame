package fr.o80.soul

import fr.o80.soul.loop.KeyPipeline

interface Scene {
    fun open(keyPipeline: KeyPipeline)
    fun close()
    suspend fun update()
    suspend fun render()
}