package fr.o80.gamelib.service.goal

// TODO The GameLib should not know anything about Mana/Score/Ticks
class GoalsChecker {
    fun getSucceededGoals(goals: Goals, data: Map<String, Any>): List<String> {
        return listOfNotNull(
            "Mana".takeIf { goals.mana.check(data["mana"]) },
            "Score".takeIf { goals.score.check(data["score"]) },
            "Ticks".takeIf { goals.ticks.check(data["ticks"]) },
        )
    }
}
