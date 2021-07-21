package fr.o80.soulgame.scenes.levelselector

import fr.o80.soulgame.data.levels.LevelsLister
import fr.o80.soulgame.data.levels.SelectableLevel

class LevelSelectorSystem(
    private val levelLister: LevelsLister = LevelsLister()
) {

    fun forEachLevel(block: (SelectableLevel) -> Unit) {
        levelLister.levels.forEach { levelName ->
            block(levelName)
        }
    }
}
