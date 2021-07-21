package fr.o80.soulgame.data.levels

import fr.o80.soulgame.data.InFileSavesRepository
import fr.o80.soulgame.data.SavesRepository
import fr.o80.soulgame.resourceFile

class LevelsLister(
    private val repository: SavesRepository = InFileSavesRepository()
) {

    val levels: List<SelectableLevel> by lazy {
        resourceFile("levels/").listFiles()?.toList()?.mapNotNull { it.name.toIntOrNull() }?.sorted()
            ?.map { levelCode -> SelectableLevel(levelCode, isUnlocked(levelCode)) }
        ?: emptyList()
    }

    private fun isUnlocked(levelCode: Int) = levelCode == 1 || repository.isUnlocked(levelCode)
}
