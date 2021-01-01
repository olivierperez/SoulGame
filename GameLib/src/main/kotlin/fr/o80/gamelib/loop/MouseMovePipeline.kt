package fr.o80.gamelib.loop

interface MouseMovePipeline {
    fun onMove(block: (Double, Double) -> Unit)
}
