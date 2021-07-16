package fr.o80.soulgame.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Saves(
    val levels: MutableMap<Int, LevelSave> = mutableMapOf()
)

@Serializable
data class LevelSave(
    val highScore: Long,
    val completedGoals: Set<String>,
    val unlocked: Boolean
) {
    companion object {
        fun empty(unlocked: Boolean = false): LevelSave = LevelSave(0L, emptySet(), unlocked)
    }
}
