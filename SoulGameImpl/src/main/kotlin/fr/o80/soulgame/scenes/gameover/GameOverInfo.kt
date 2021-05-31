package fr.o80.soulgame.scenes.gameover

import fr.o80.soulgame.scenes.level.level.LevelSettings

data class GameOverInfo(
    val levelSettings: LevelSettings,
    val score: Long
)
