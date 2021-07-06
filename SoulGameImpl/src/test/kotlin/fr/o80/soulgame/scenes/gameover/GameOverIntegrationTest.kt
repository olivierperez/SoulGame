package fr.o80.soulgame.scenes.gameover

import ch.tutteli.atrium.api.fluent.en_GB.contains
import ch.tutteli.atrium.api.fluent.en_GB.inAnyOrder
import ch.tutteli.atrium.api.fluent.en_GB.only
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.fluent.en_GB.values
import ch.tutteli.atrium.api.verbs.expect
import fr.o80.gamelib.service.condition.Condition
import fr.o80.gamelib.service.condition.ConditionResolver
import fr.o80.gamelib.service.condition.LongValueOperand
import fr.o80.gamelib.service.condition.Operation
import fr.o80.gamelib.service.condition.ParamValueOperand
import fr.o80.gamelib.service.goal.Goal
import fr.o80.soulgame.data.SavesRepository
import fr.o80.soulgame.data.model.LevelSave
import fr.o80.soulgame.data.model.Saves
import fr.o80.soulgame.scenes.level.level.LevelSettings
import fr.o80.soulgame.scenes.level.level.ManaConfig
import fr.o80.soulgame.scenes.level.level.SpritesConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

private const val manaGoalName = "Mana's goal"
private const val scoreGoalName = "Score's goal"
private const val ticksGoalName = "Tick's goal"

private val atMostManaIs26 = Goal(
    manaGoalName, Condition(
        ParamValueOperand("mana"),
        LongValueOperand(26L),
        Operation.LTE
    )
)

private val atLeastScoreIs25 = Goal(
    scoreGoalName, Condition(
        ParamValueOperand("score"),
        LongValueOperand(25L),
        Operation.GTE
    )
)

private val atLeastScoreIs50 = Goal(
    scoreGoalName, Condition(
        ParamValueOperand("score"),
        LongValueOperand(50L),
        Operation.GTE
    )
)

private val atLeastTicksIs200 = Goal(
    ticksGoalName, Condition(
        ParamValueOperand("ticks"),
        LongValueOperand(200L),
        Operation.GTE
    )
)

@DisplayName("GameOverSystem")
internal class GameOverIntegrationTest {

    @Test
    @DisplayName("can save for the first time")
    fun shouldSaveForTheFirstTime() {
        // Given
        val repository = TestRepository(saves = Saves())
        val conditionResolver = ConditionResolver()
        val gameOverSystem = GameOverSystem(
            GameOverInfo(
                levelSettings = createLevelSettingsWith(
                    7,
                    goals = listOf(
                        atMostManaIs26,
                        atLeastScoreIs25,
                        atLeastTicksIs200,
                    )
                ),
                remainingMana = 0,
                score = 54L,
                ticks = 245L
            ),
            repository = repository,
            conditionResolver = conditionResolver
        )

        // When
        gameOverSystem.save()

        // Then
        val levelSaved = repository.saves.levels[7]!!
        expect(levelSaved.highScore).toBe(54L)
        expect(levelSaved.completedGoals)
            .contains.inAnyOrder.only.values(scoreGoalName, manaGoalName, ticksGoalName)
    }

    @Test
    @DisplayName("can update the previous goals")
    fun shouldUpdatePreviousGoals() {
        // Given
        val repository = TestRepository(
            saves = Saves(
                mutableMapOf(
                    8 to LevelSave(
                        55L,
                        setOf("goal1")
                    )
                )
            )
        )
        val conditionResolver = ConditionResolver()
        val gameOverSystem = GameOverSystem(
            GameOverInfo(
                levelSettings = createLevelSettingsWith(
                    8,
                    goals = listOf(
                        atMostManaIs26,
                        atLeastScoreIs50,
                        atLeastTicksIs200,
                    )
                ),
                remainingMana = 100,
                score = 54L,
                ticks = 1L
            ),
            repository = repository,
            conditionResolver = conditionResolver
        )

        // When
        gameOverSystem.save()

        // Then
        val levelSaved = repository.saves.levels[8]!!
        expect(levelSaved.highScore).toBe(55L)
        expect(levelSaved.completedGoals)
            .contains.inAnyOrder.only.values(scoreGoalName, "goal1")
    }

    @Test
    @DisplayName("can update the high score")
    fun shouldUpdatePreviousHighScore() {
        // Given
        val repository = TestRepository(
            saves = Saves(
                mutableMapOf(
                    9 to LevelSave(
                        13L,
                        setOf(scoreGoalName)
                    )
                )
            )
        )
        val conditionResolver = ConditionResolver()
        val gameOverSystem = GameOverSystem(
            GameOverInfo(
                levelSettings = createLevelSettingsWith(
                    9,
                    goals = listOf(
                        atMostManaIs26,
                        atLeastScoreIs50,
                        atLeastTicksIs200,
                    )
                ),
                remainingMana = 100,
                score = 15L,
                ticks = 2L
            ),
            repository = repository,
            conditionResolver = conditionResolver
        )

        // When
        gameOverSystem.save()

        // Then
        val levelSaved = repository.saves.levels[9]!!
        expect(levelSaved.highScore).toBe(15L)
        expect(levelSaved.completedGoals)
            .contains.inAnyOrder.only.values(scoreGoalName)
    }
}

class TestRepository(val saves: Saves) : SavesRepository {

    override fun update(apply: (Saves) -> Unit) {
        apply(saves)
    }

    override fun getHighScore(levelCode: Int): Long {
        error("High score should not be called for this tests suite!")
    }
}

private fun createLevelSettingsWith(code: Int, goals: List<Goal>): LevelSettings {
    return LevelSettings(
        code = code,
        name = "name",
        mana = ManaConfig(
            gainAtPortal = 0,
            gainAtConversion = 0,
            initial = 0,
            loss = 0,
            max = 0,
        ),
        font = "font",
        sprite = SpritesConfig(
            characters = "characters",
            doors = "doors",
            walls = "walls",
        ),
        endWhen = Condition(LongValueOperand(1), LongValueOperand(2), Operation.LTE),
        goals = goals
    )
}
