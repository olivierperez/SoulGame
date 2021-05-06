package fr.o80.soulgame

import kotlinx.serialization.Serializable

@Serializable
data class SoulGameData(
    val bestScores: MutableMap<String, Long> = mutableMapOf()
)
