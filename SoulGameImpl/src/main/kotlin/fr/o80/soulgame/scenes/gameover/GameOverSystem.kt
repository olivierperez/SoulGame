package fr.o80.soulgame.scenes.gameover

import fr.o80.gamelib.service.condition.Condition
import fr.o80.gamelib.service.condition.ConditionResolver
import fr.o80.gamelib.service.goal.Goal
import fr.o80.soulgame.data.InFileSavesRepository
import fr.o80.soulgame.data.SavesRepository
import fr.o80.soulgame.data.model.LevelSave
import kotlin.math.min

class GameOverSystem(
    private val info: GameOverInfo,
    private val repository: SavesRepository = InFileSavesRepository(),
    private val conditionResolver: ConditionResolver = ConditionResolver()
) {

    fun update(state: GameOverState) {
        state.bestScore = repository.getHighScore(info.levelSettings.code)
        state.score = min(state.score + 1, info.score)
    }

    fun save() {
        val levelData = mapOf(
            "mana" to info.remainingMana.toLong(),
            "score" to info.score,
            "ticks" to info.ticks
        )

        val succeededGoals = info.levelSettings.goals.filterSucceededGoals(levelData)
        val levelCode = info.levelSettings.code

        repository.update { saves ->
            val levelSave = saves.levels[levelCode] ?: LevelSave.empty()

            val newBestScore = levelSave.highScore.coerceAtLeast(info.score)
            val newCompletedGoals = levelSave.completedGoals + succeededGoals

            val newLevelSave = LevelSave(
                highScore = newBestScore,
                completedGoals = newCompletedGoals
            )

            saves.levels[levelCode] = newLevelSave
        }
    }

    private fun List<Goal>.filterSucceededGoals(levelData: Map<String, Long>): List<String> =
        this.filter { (_, condition: Condition) -> conditionResolver.resolve(condition, levelData) }
            .map(Goal::name)
}
