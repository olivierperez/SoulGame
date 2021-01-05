package fr.o80.gamelib.loop

interface MouseButtonPipeline {
    fun onButton(button: Int, action: Int, block: (x: Double, y: Double) -> Unit)
}
