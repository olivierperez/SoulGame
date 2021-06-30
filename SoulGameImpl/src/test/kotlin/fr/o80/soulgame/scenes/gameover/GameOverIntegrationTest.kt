package fr.o80.soulgame.scenes.gameover

import fr.o80.gamelib.service.condition.Condition
import fr.o80.gamelib.service.condition.IntValueOperand
import fr.o80.gamelib.service.condition.Operation
import fr.o80.gamelib.service.goal.Goals
import fr.o80.gamelib.service.goal.MaxGoal
import fr.o80.gamelib.service.goal.MinGoal
import fr.o80.soulgame.data.SavesRepository
import fr.o80.soulgame.data.model.LevelSave
import fr.o80.soulgame.data.model.Saves
import fr.o80.soulgame.scenes.level.level.LevelSettings
import fr.o80.soulgame.scenes.level.level.ManaConfig
import fr.o80.soulgame.scenes.level.level.SpritesConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("GameOverSystem")
internal class GameOverIntegrationTest {

    @Test
    @DisplayName("can save for the first time")
    fun shouldSaveForTheFirstTime() {
        // Given
        val repository = TestRepository(saves = Saves())
        val gameOverSystem = GameOverSystem(
            GameOverInfo(
                levelSettings = createLevelSettingsWith(
                    7,
                    Goals(
                        mana = MaxGoal(26L),
                        score = MinGoal(50L),
                        ticks = MinGoal(200L)
                    )
                ),
                remainingMana = 0,
                score = 54L,
                ticks = 245L
            ),
            repository = repository
        )

        // When
        gameOverSystem.save()

        // Then
        val levelSaved = repository.saves.levels[7]!!
        assertEquals(54L, levelSaved.highScore)
        assertEquals(3, levelSaved.completedGoals.size)
        assertTrue(levelSaved.completedGoals.containsAll(listOf("Score", "Mana", "Ticks")))
    }

    @Test
    @DisplayName("can update the previous goals")
    fun shouldUpdatePreviousGoals() {
        // Given
        val repository = TestRepository(saves = Saves(
            mutableMapOf(
                8 to LevelSave(
                    55L,
                    setOf("goal1")
                )
            )
        ))
        val gameOverSystem = GameOverSystem(
            GameOverInfo(
                levelSettings = createLevelSettingsWith(
                    8,
                    Goals(
                        mana = MaxGoal(26L),
                        score = MinGoal(50L),
                        ticks = MinGoal(200L)
                    )
                ),
                remainingMana = 100,
                score = 54L,
                ticks = 1L
            ),
            repository = repository
        )

        // When
        gameOverSystem.save()

        // Then
        val levelSaved = repository.saves.levels[8]!!
        assertEquals(55L, levelSaved.highScore)
        assertEquals(2, levelSaved.completedGoals.size)
        assertTrue(levelSaved.completedGoals.containsAll(listOf("Score", "goal1")))
    }

    @Test
    @DisplayName("can update the high score")
    fun shouldUpdatePreviousHighScore() {
        // Given
        val repository = TestRepository(saves = Saves(
            mutableMapOf(
                9 to LevelSave(
                    13L,
                    setOf("Score")
                )
            )
        ))
        val gameOverSystem = GameOverSystem(
            GameOverInfo(
                levelSettings = createLevelSettingsWith(
                    9,
                    Goals(
                        mana = MaxGoal(26L),
                        score = MinGoal(50L),
                        ticks = MinGoal(200L)
                    )
                ),
                remainingMana = 100,
                score = 15L,
                ticks = 2L
            ),
            repository = repository
        )

        // When
        gameOverSystem.save()

        // Then
        val levelSaved = repository.saves.levels[9]!!
        assertEquals(15L, levelSaved.highScore)
        assertEquals(1, levelSaved.completedGoals.size)
        assertTrue(levelSaved.completedGoals.containsAll(listOf("Score")))
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

private fun createLevelSettingsWith(code: Int, goals: Goals): LevelSettings {
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
        endWhen = Condition(IntValueOperand(1), IntValueOperand(2), Operation.LTE),
        goals = goals
    )
}
