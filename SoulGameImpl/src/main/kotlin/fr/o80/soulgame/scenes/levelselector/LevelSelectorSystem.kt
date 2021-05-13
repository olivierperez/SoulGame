package fr.o80.soulgame.scenes.levelselector

import fr.o80.soulgame.resourceFile

class LevelSelectorSystem {

    private val levelNames: List<String> by lazy {
        resourceFile("levels/").list()?.toList()?.map { it.removeSuffix(".txt") }
        ?: emptyList()
    }

    fun forEachLevel(block: (String) -> Unit) {
        levelNames.forEach { levelName ->
            block(levelName)
        }
    }
}
