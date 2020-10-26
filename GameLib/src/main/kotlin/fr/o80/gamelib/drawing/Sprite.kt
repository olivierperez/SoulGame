package fr.o80.gamelib.drawing

import fr.o80.gamelib.dsl.Vertex2f

class Sprite(
    val image: Image,
    val unitWidth: Int,
    val unitHeight: Int,
    private val marginTop: Int = 0,
    private val marginLeft: Int = 0,
    private val horizontalPadding: Int = 0,
    private val verticalPadding: Int = 0
) {

    fun computeUnitSprite(x: Int, y: Int): Pair<Vertex2f, Vertex2f> {
        val x1: Float = (marginLeft + x * (unitWidth + horizontalPadding)).toFloat()
        val y1: Float = (marginTop + y * (unitHeight + verticalPadding)).toFloat()
        val x2: Float = x1 + unitWidth
        val y2: Float = y1 + unitHeight
        return Pair(
            Vertex2f(x1 / image.width, y1 / image.height),
            Vertex2f(x2 / image.width, y2 / image.height)
        )
    }

}