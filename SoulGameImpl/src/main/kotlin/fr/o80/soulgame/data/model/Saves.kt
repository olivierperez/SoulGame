package fr.o80.soulgame.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Saves(
    val levels: MutableMap<Int, LevelSave> = mutableMapOf()
)

@Serializable
data class LevelSave(
    val highScore: Long,
    val completedGoals: Set<String>
) {
    companion object {
        fun empty(): LevelSave = LevelSave(0L, emptySet())
    }
}
