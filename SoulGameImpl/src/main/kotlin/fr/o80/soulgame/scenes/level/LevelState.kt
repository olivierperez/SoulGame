package fr.o80.soulgame.scenes.level

import fr.o80.soulgame.scenes.level.entity.Knight
import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.level.Level

class LevelState(
    val level: Level,
    val mob: List<Soul>,
    val knight: Knight,
    val score: Score
)
