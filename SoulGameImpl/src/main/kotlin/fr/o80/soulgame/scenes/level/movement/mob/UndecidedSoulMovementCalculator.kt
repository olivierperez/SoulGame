package fr.o80.soulgame.scenes.level.movement.mob

import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.level.Level
import fr.o80.soulgame.scenes.level.level.Point
import fr.o80.soulgame.scenes.level.movement.Direction
import fr.o80.soulgame.scenes.level.movement.EntityMovementCalculator
import fr.o80.soulgame.scenes.level.movement.GlobalMovement

class UndecidedSoulMovementCalculator(
    private val level: Level,
    private val tileSize: Float,
    private val globalMovement: GlobalMovement
) : EntityMovementCalculator<Soul> {

    override fun update(soul: Soul) {
        val canContinue = soul.direction != null && globalMovement.canGo(soul, level, soul.direction!!, soul.speed)

        if (isOnLastDecisionTile(soul) && canContinue) {
            globalMovement.goIn(soul, soul.direction, soul.speed)
            return
        }

        if (awayFromTileCenter(soul, tileSize) && canContinue) {
            globalMovement.goIn(soul, soul.direction, soul.speed)
            return
        }

        maybeChangeDirection(soul)
    }

    private fun maybeChangeDirection(soul: Soul) {
        val lastDirection = soul.direction
        val availableDirections = Direction.values()
            .filter { direction -> globalMovement.canGo(soul, level, direction, tileSize) }
            .toMutableList()

        val direction = when {
            availableDirections.size >= 2 -> {
                lastDirection?.let {
                    availableDirections.remove(lastDirection.opposite)
                }
                val randomDirection = availableDirections.random()
                randomDirection
            }
            lastDirection != null && globalMovement.canGo(soul, level, lastDirection, soul.speed) -> {
                soul.direction
            }
            availableDirections.size == 1 -> {
                availableDirections[0]
            }
            else -> {
                null
            }
        }

        globalMovement.goIn(soul, direction, soul.speed)
        soul.lastDecisionTile = Point((soul.x / tileSize).toInt(), (soul.y / tileSize).toInt())
    }

    private fun isOnLastDecisionTile(entity: Soul): Boolean {
        val tileX = (entity.x / tileSize).toInt()
        val tileY = (entity.y / tileSize).toInt()
        return entity.lastDecisionTile.x == tileX && entity.lastDecisionTile.y == tileY
    }

    private fun awayFromTileCenter(soul: Soul, tileSize: Float): Boolean {
        val deltaX = (soul.x - tileSize / 2) % tileSize
        val deltaY = (soul.y - tileSize / 2) % tileSize
        return deltaX > soul.speed / 2 || deltaY > soul.speed / 2
    }

}
