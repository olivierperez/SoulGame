package fr.o80.soulgame.scenes.level.loading

import fr.o80.soulgame.scenes.level.level.Level
import java.io.InputStream

class LevelLoader {

    fun load(source: InputStream): Level {
        val headerReader = LevelHeaderReader()
        val terrainReader = LevelTerrainReader()

        var readingHeader = true

        source.bufferedReader().use { reader ->
            reader.readLines().forEachIndexed { y, line ->
                if (line.isBlank()) {
                    println("Fin des headers")
                    readingHeader = false
                    return@forEachIndexed
                }

                if (readingHeader) {
                    headerReader.read(line)
                } else {
                    terrainReader.read(line)
                }

            }
        }

        return Level(
            settings = headerReader.build(),
            terrain = terrainReader.build()
        )
    }
}
