package fr.o80.soulgame.scenes.level.loading

import fr.o80.soulgame.scenes.level.level.Block
import fr.o80.soulgame.scenes.level.level.Door
import fr.o80.soulgame.scenes.level.level.Point
import fr.o80.soulgame.scenes.level.level.Terrain
import fr.o80.soulgame.scenes.level.level.Wall

class LevelTerrainReader {

    private var y: Int = 0

    private val _blocks = mutableListOf<Block>()
    private val _mobSpawns = mutableListOf<Point>()
    private var _knightSpawn: Point? = null

    fun read(line: String) {
        line.forEachIndexed { x, char ->
            when (char) {
                '#' -> _blocks += Wall(x, y)
                '+' -> _knightSpawn = Point(x, y)
                ':' -> _mobSpawns += Point(x, y)
                'A' -> _blocks += Door(x, y)
            }
        }

        y++
    }

    fun build() : Terrain {
        return Terrain(
            blocks = _blocks,
            mobSpawns = _mobSpawns,
            knightSpawn = _knightSpawn ?: throw MalformedLevelFile("Knight spawn is not set! (use '+')")
        )
    }
}
