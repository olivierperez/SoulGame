package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.Position
import fr.o80.soulgame.scenes.level.level.Point

fun Point.toPosition(tileSize: Float): Position {
    return Position(
        x = x * tileSize + tileSize / 2f,
        y = y * tileSize + tileSize / 2f
    )
}
