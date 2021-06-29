package fr.o80.soulgame.scenes.levelselector

import fr.o80.soulgame.resourceFile

class LevelSelectorSystem {

    private val levelNames: List<Int> by lazy {
        resourceFile("levels/").listFiles()?.toList()?.mapNotNull { it.name.toIntOrNull() }?.sorted()
        ?: emptyList()
    }

    fun forEachLevel(block: (Int) -> Unit) {
        levelNames.forEach { levelName ->
            block(levelName)
        }
    }
}
