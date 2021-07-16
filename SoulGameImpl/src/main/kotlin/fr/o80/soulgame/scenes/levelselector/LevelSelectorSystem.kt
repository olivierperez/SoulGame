package fr.o80.soulgame.scenes.levelselector

import fr.o80.soulgame.data.InFileSavesRepository
import fr.o80.soulgame.data.SavesRepository
import fr.o80.soulgame.resourceFile

class LevelSelectorSystem(
    private val repository: SavesRepository = InFileSavesRepository()
) {

    private val levels: List<SelectableLevel> by lazy {
        resourceFile("levels/").listFiles()?.toList()?.mapNotNull { it.name.toIntOrNull() }?.sorted()
            ?.map { levelCode -> SelectableLevel(levelCode, isUnlocked(levelCode)) }
        ?: emptyList()
    }

    fun forEachLevel(block: (SelectableLevel) -> Unit) {
        levels.forEach { levelName ->
            block(levelName)
        }
    }

    private fun isUnlocked(levelCode: Int) = levelCode == 1 || repository.isUnlocked(levelCode)
}
