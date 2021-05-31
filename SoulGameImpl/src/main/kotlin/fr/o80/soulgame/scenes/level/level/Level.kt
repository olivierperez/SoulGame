package fr.o80.soulgame.scenes.level.level

import fr.o80.soulgame.scenes.level.entity.Entity
import kotlin.math.abs

class Level(
    val settings: LevelSettings,
    val terrain: Terrain,
)

class LevelSettings(
    val name: String,
    val mana: ManaConfig,
    val font: String,
    val sprite: SpritesConfig,
)

class ManaConfig(
    val gainAtPortal: Int,
    val gainAtConversion: Int,
    val initial: Int,
    val loss: Int,
)

class SpritesConfig(
    val characters: String,
    val doors: String,
    val walls: String,
)

class Terrain(
    val blocks: List<Block>,
    val knightSpawn: Point,
    val mobSpawns: List<Point>
) {
    val width: Int = blocks.map { it.x + 1 }.maxOrNull() ?: 0
    val height: Int = blocks.map { it.y + 1 }.maxOrNull() ?: 0

    val upperEnd: Door = blocks.filterIsInstance(Door::class.java).first()

    fun hasWall(x: Int, y: Int): Boolean {
        return blocks.filterIsInstance(Wall::class.java).any { it.x == x && it.y == y }
    }

    fun canGo(entity: Entity, x: Int, y: Int): Boolean {
        return blocks.filter { it.x == x && it.y == y }.all { it.canAccept(entity) }
    }

    fun hasSameBlock(block: Block, x: Int, y: Int): Boolean {
        return blocks
            .asSequence()
            .filterIsInstance(block::class.java)
            .any { it.x == x && it.y == y }
    }
}

class Point(val x: Int, val y: Int) {
    fun distanceFrom(x: Int, y: Int): Int {
        return abs(y - this.y) + abs(x - this.x)
    }
}
