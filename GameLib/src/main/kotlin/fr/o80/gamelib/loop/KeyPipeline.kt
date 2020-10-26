package fr.o80.gamelib.loop

interface KeyPipeline {
    fun onKey(key: Int, action: Int, block: () -> Unit)
}
