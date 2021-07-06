package fr.o80.gamelib.service.goal

import fr.o80.gamelib.service.condition.Condition

data class Goal(
    val name: String,
    val condition: Condition
)
