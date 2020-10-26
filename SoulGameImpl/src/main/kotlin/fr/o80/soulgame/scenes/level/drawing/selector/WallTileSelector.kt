package fr.o80.soulgame.scenes.level.drawing.selector

import fr.o80.soulgame.scenes.level.level.Block
import fr.o80.soulgame.scenes.level.level.Level

class WallTileSelector(
    private val level: Level
) : TileSelector {

    override fun get(block: Block, x: Int, y: Int): Pair<Int, Int> {
        val tilePosition = computeTile(block, x, y)
        return Pair(tilePosition.x, tilePosition.y)
    }

    private fun computeTile(block: Block, x: Int, y: Int): TilePosition {
        val wallUp = level.hasSameBlock(block, x, y - 1)
        val wallRight = level.hasSameBlock(block, x + 1, y)
        val wallDown = level.hasSameBlock(block, x, y + 1)
        val wallLeft = level.hasSameBlock(block, x - 1, y)

        return withWallsArround(wallUp, wallRight, wallDown, wallLeft)
    }

    private fun withWallsArround(up: Boolean, right: Boolean, bottom: Boolean, left: Boolean): TilePosition {
        return TilePosition.values().first { it.up == up && it.right == right && it.bottom == bottom && it.left == left }
    }
}

enum class TilePosition(
    val x: Int,
    val y: Int,
    val up: Boolean,
    val right: Boolean,
    val bottom: Boolean,
    val left: Boolean
) {
    RIGHT_BOTTOM_LEFT(0, 0, false, true, true, true),
    TOP_RIGHT_LEFT(1, 0, true, true, false, true),
    RIGHT_BOTTOM(2, 0, false, true, true, false),
    BOTTOM_LEFT(3, 0, false, false, true, true),
    TOP_RIGHT_BOTTOM(0, 1, true, true, true, false),
    TOP_BOTTOM_LEFT(1, 1, true, false, true, true),
    TOP_RIGHT(2, 1, true, true, false, false),
    TOP_LEFT(3, 1, true, false, false, true),

    TOP(0, 2, true, false, false, false),
    RIGHT(1, 2, false, true, false, false),
    BOTTOM(2, 2, false, false, true, false),
    LEFT(3, 2, false, false, false, true),

    ALL(0, 3, true, true, true, true),
    VERTICAL(1, 3, true, false, true, false),
    HORIZONTAL(2, 3, false, true, false, true),
    NONE(3, 3, false, false, false, false);
}