package fr.o80.gamelib.service.goal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("GoalsChecker")
internal class GoalsCheckerTest {

    private val goalsChecker = GoalsChecker()

    private val goals = Goals(
        mana = MaxGoal(12L),
        score = MinGoal(50L),
        ticks = MinGoal(200L)
    )

    @Test
    @DisplayName("can complete all goals")
    fun shouldCompleteAllGoals() {
        // Given
        val data = mutableMapOf<String, Any>(
            "score" to 54L,
            "mana" to 10,
            "ticks" to 2354L
        )

        // When
        val succeededGoals = goalsChecker.getSucceededGoals(goals, data)

        // Then
        assertEquals(1, succeededGoals.size)
        assertTrue(succeededGoals.contains("Score"))
    }

    @Test
    @DisplayName("do not complete any goals")
    fun shouldNotCompleteAnyGoals() {
        // Given
        val data = mutableMapOf<String, Any>(
            "score" to 13L,
            "mana" to 5487,
            "ticks" to 27L
        )

        // When
        val succeededGoals = goalsChecker.getSucceededGoals(goals, data)

        // Then
        assertEquals(0, succeededGoals.size)
    }
}