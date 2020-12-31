package fr.o80.gamelib.loop

interface MouseButtonPipeline {
    fun onButton(button: Int, action: Int, block: (x: Float, y: Float) -> Unit)
}
