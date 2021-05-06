package fr.o80.soulgame.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BestScores(
    val levels: MutableMap<String, Long> = mutableMapOf()
)
