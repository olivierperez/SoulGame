package fr.o80.soul.loop

interface KeyPipeline {
    fun onKey(key: Int, action: Int, block: () -> Unit)
}
