package fr.o80.soulgame.scenes.level.movement

import fr.o80.gamelib.Angles
import fr.o80.gamelib.Position
import fr.o80.soulgame.scenes.level.entity.Entity
import fr.o80.soulgame.scenes.level.level.Level
import kotlin.math.max

class GlobalMovement(
    private val tileSize: Float,
    private val entityUnits: Pair<Int, Int>
) {

    fun canGo(
        entity: Entity,
        level: Level,
        direction: Direction,
        speed: Float,
        adjustX: Float = 0f,
        adjustY: Float = 0f
    ): Boolean {
        val adjustedX = entity.x + adjustX
        val adjustedY = entity.y + adjustY
        val nextPosition: Position = when (direction) {
            Direction.UP -> Position(adjustedX, adjustedY - speed)
            Direction.RIGHT -> Position(adjustedX + speed, adjustedY)
            Direction.DOWN -> Position(adjustedX, adjustedY + speed)
            Direction.LEFT -> Position(adjustedX - speed, adjustedY)
        }

        val (topLeft, bottomRight) = entity.getAngles(nextPosition)

        return level.canGo(entity, (topLeft.x / tileSize).toInt(), (topLeft.y / tileSize).toInt()) &&
               level.canGo(entity, (bottomRight.x / tileSize).toInt(), (topLeft.y / tileSize).toInt()) &&
               level.canGo(entity, (bottomRight.x / tileSize).toInt(), (bottomRight.y / tileSize).toInt()) &&
               level.canGo(entity, (topLeft.x / tileSize).toInt(), (bottomRight.y / tileSize).toInt())
    }

    fun goIn(entity: Entity, direction: Direction?, speed: Float) {
        when (direction) {
            Direction.RIGHT -> {
                entity.x += speed
                entity.direction = Direction.RIGHT
                entity.movement = Movement.MOVING
            }
            Direction.LEFT -> {
                entity.x -= speed
                entity.direction = Direction.LEFT
                entity.movement = Movement.MOVING
            }
            Direction.UP -> {
                entity.y -= speed
                entity.direction = Direction.UP
                entity.movement = Movement.MOVING
            }
            Direction.DOWN -> {
                entity.y += speed
                entity.direction = Direction.DOWN
                entity.movement = Movement.MOVING
            }
            else -> {
                entity.movement = Movement.STANDING
            }
        }
    }

    private fun Entity.getAngles(nextPosition: Position = Position(x, y)): Angles {
        val unitWidth = entityUnits.first
        val unitHeight = entityUnits.second
        val ratio = this.size / max(unitWidth, unitHeight)
        val width = unitWidth * ratio
        val height = unitHeight * ratio

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

}