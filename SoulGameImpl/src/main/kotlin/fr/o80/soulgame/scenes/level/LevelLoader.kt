package fr.o80.soulgame.scenes.level

import fr.o80.soulgame.resourceFile
import fr.o80.soulgame.scenes.level.level.Block
import fr.o80.soulgame.scenes.level.level.Door
import fr.o80.soulgame.scenes.level.level.Level
import fr.o80.soulgame.scenes.level.level.Point
import fr.o80.soulgame.scenes.level.level.Wall

class LevelLoader {

    fun load(levelName: String): Level {

        val source = resourceFile("levels/$levelName.txt").inputStream()

        val blocks = mutableListOf<Block>()
        val mobSpawns = mutableListOf<Point>()
        var knightSpawn: Point? = null

        source.bufferedReader().use { reader ->
            reader.readLines().forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    when (char) {
                        '#' -> blocks += Wall(x, y)
                        '+' -> knightSpawn = Point(x, y)
                        ':' -> mobSpawns += Point(x, y)
                        'A' -> blocks += Door(x, y)
                    }
                }
            }
        }

        return Level(
            blocks = blocks,
            knightSpawn = knightSpawn ?: throw IllegalArgumentException("$levelName doesn't set the Knight spawn '+'!"),
            mobSpawns = mobSpawns
        )

    }

}
