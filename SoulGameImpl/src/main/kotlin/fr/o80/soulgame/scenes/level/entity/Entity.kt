package fr.o80.soulgame.scenes.level.entity

import fr.o80.gamelib.Angles
import fr.o80.gamelib.Position
import fr.o80.gamelib.drawing.Sprite
import fr.o80.soulgame.scenes.level.movement.Direction
import fr.o80.soulgame.scenes.level.movement.Movement
import kotlin.math.max

abstract class Entity(
    var x: Float,
    var y: Float,
    val size: Float,
    val sprite: Sprite,
    val speed: Float,
    var direction: Direction? = null,
    var movement: Movement = Movement.STANDING
) {

    abstract val characterIndex: Int

    fun getAngles(nextPosition: Position = Position(x, y)): Angles {
        val ratio = this.size / max(this.sprite.unitWidth, this.sprite.unitHeight)
        val width = this.sprite.unitWidth * ratio
        val height = this.sprite.unitHeight * ratio

        val topLeft = Position(
            x = nextPosition.x - (width / 2),
            y = nextPosition.y - (height / 2)
        )
        val bottomRight = Position(
            x = nextPosition.x + (width / 2),
            y = nextPosition.y + (height / 2)
        )

        return Angles(topLeft, bottomRight)
    }

    fun respawn(spawnPosition: Position) {
        x = spawnPosition.x
        y = spawnPosition.y
    }

}
