package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.camera.Camera
import fr.o80.gamelib.dsl.Vertex2d

class LevelCamera(
    private val camera: Camera,
    private val levelSize: Vertex2d,
    private val margin: Double
) {

    fun update(levelState: LevelState) {
        adjustHorizontal(levelState.knight.x)
        adjustVertical(levelState.knight.y)
    }

    private fun adjustHorizontal(knightX: Float) {
        val expectedRight = (knightX + margin).coerceAtMost(levelSize.x)
        val moveRight = expectedRight - camera.right
        if (moveRight > 0) {
            camera.translateX(moveRight)
        }

        val expectedLeft = (knightX - margin).coerceAtLeast(.0)
        val moveLeft = expectedLeft - camera.left
        if (moveLeft < 0) {
            camera.translateX(moveLeft)
        }
    }

    private fun adjustVertical(knightY: Float) {
        val expectedBottom = (knightY + margin).coerceAtMost(levelSize.y)
        val moveBottom = expectedBottom - camera.bottom
        if (moveBottom > 0) {
            camera.translateY(moveBottom)
        }

        val expectedTop = (knightY - margin).coerceAtLeast(.0)
        val moveTop = expectedTop - camera.top
        if (moveTop < 0) {
            camera.translateY(moveTop)
        }
    }
}
