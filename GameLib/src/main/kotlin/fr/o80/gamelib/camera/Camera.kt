package fr.o80.gamelib.camera

import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.dsl.draw

class Camera(
    val width: Double,
    val height: Double
) {

    private var x: Double = .0
    private var y: Double = .0

    val top: Double
        get() = y

    val right: Double
        get() = x + width

    val bottom: Double
        get() = y + height

    val left: Double
        get() = x

    suspend fun render(block: suspend Draw.() -> Unit) {
        draw {
            pushed {
                translate(-x, -y, .0)
                block()
            }
        }
    }

    fun translateX(translation: Double) {
        this.x += translation
    }

    fun translateY(translation: Double) {
        this.y += translation
    }

    fun centerX(center: Double) {
        this.x = center - width / 2
    }

    fun centerY(center: Double) {
        this.y = center - height / 2
    }
}
