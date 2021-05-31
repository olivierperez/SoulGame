package fr.o80.soulgame.scenes.level.movement

import fr.o80.soulgame.scenes.level.entity.Knight
import fr.o80.soulgame.scenes.level.level.Terrain

class KnightMovementCalculator(
    private val terrain: Terrain,
    private val tileSize: Float,
    private val adjustmentTolerance: Float,
    private val globalMovement: GlobalMovement
) : EntityMovementCalculator<Knight> {

    private val directions: MutableList<Direction> = mutableListOf()

    override fun update(knight: Knight) {

        knight.movement = Movement.STANDING
        for (direction in directions) {
            if (globalMovement.canGo(knight, terrain, direction, knight.speed)) {
                globalMovement.goIn(knight, direction, knight.speed)
                return
            }

            tryToAdjust(knight, direction)?.let { adjustedDirection ->
                globalMovement.goIn(knight, adjustedDirection, knight.speed)
                return
            }
        }
    }

    private fun tryToAdjust(knight: Knight, direction: Direction): Direction? {
        val adjustmentPercentage = (adjustmentTolerance * 100).toInt()
        return if (direction.isHorizontal) {
            (1..adjustmentPercentage).mapNotNull { tolerance ->
                when {
                    globalMovement.canGo(
                        knight,
                        terrain,
                        direction,
                        knight.speed,
                        adjustY = -tileSize * tolerance / 100f
                    ) ->
                        Direction.UP
                    globalMovement.canGo(
                        knight,
                        terrain,
                        direction,
                        knight.speed,
                        adjustY = tileSize * tolerance / 100f
                    ) ->
                        Direction.DOWN
                    else ->
                        null
                }
            }.firstOrNull()
        } else {
            (1..adjustmentPercentage).mapNotNull { tolerance ->
                when {
                    globalMovement.canGo(
                        knight,
                        terrain,
                        direction,
                        knight.speed,
                        adjustX = -tileSize * tolerance / 100f
                    ) ->
                        Direction.LEFT
                    globalMovement.canGo(
                        knight,
                        terrain,
                        direction,
                        knight.speed,
                        adjustX = tileSize * tolerance / 100f
                    ) ->
                        Direction.RIGHT
                    else ->
                        null
                }
            }.firstOrNull()
        }
    }

    fun pressedKey(direction: Direction) {
        directions.add(0, direction)
    }

    fun releasedKey(direction: Direction) {
        directions.remove(direction)
    }

}

