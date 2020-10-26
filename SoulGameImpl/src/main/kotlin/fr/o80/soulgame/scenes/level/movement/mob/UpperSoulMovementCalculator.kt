package fr.o80.soulgame.scenes.level.movement.mob

import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.level.Level
import fr.o80.soulgame.scenes.level.level.Point
import fr.o80.soulgame.scenes.level.movement.Direction
import fr.o80.soulgame.scenes.level.movement.EntityMovementCalculator
import fr.o80.soulgame.scenes.level.movement.GlobalMovement
import fr.o80.soulgame.scenes.level.movement.Movement
import fr.o80.soulgame.scenes.level.movement.ShortPathCalculator

class UpperSoulMovementCalculator(
    level: Level,
    private val tileSize: Float,
    private val globalMovement: GlobalMovement
) : EntityMovementCalculator<Soul> {

    private val shortPathCalculator = ShortPathCalculator(level)
    private val destination: Point = level.upperEnd.let { Point(it.x, it.y) }

    override fun update(soul: Soul) {
        if (awayFromTileCenter(soul, tileSize)) {
            globalMovement.goIn(soul, soul.direction, soul.speed)
            return
        }

        maybeChangeDirection(soul)
    }

    private fun maybeChangeDirection(soul: Soul) {
        val currentPosition = Point((soul.x / tileSize).toInt(), (soul.y / tileSize).toInt())
        val path = shortPathCalculator.shortPath(soul, currentPosition, destination)

        if (path.size < 2) {
            soul.movement = Movement.STANDING
            return
        }

        val nextPosition = path[1]

        when {
            nextPosition.x < currentPosition.x -> globalMovement.goIn(soul, Direction.LEFT, soul.speed)
            nextPosition.x > currentPosition.x -> globalMovement.goIn(soul, Direction.RIGHT, soul.speed)
            nextPosition.y < currentPosition.y -> globalMovement.goIn(soul, Direction.UP, soul.speed)
            nextPosition.y > currentPosition.y -> globalMovement.goIn(soul, Direction.DOWN, soul.speed)
        }

        soul.lastDecisionTile = currentPosition
    }

    private fun awayFromTileCenter(soul: Soul, tileSize: Float): Boolean {
        val deltaX = (soul.x - tileSize / 2) % tileSize
        val deltaY = (soul.y - tileSize / 2) % tileSize
        return deltaX > soul.speed / 2 || deltaY > soul.speed / 2
    }

}
