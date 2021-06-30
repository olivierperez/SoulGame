package fr.o80.gamelib.service.goal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class GoalParserTest {

    private val goalParser = GoalParser()

    @Test
    @DisplayName("Parse as minimum value")
    fun shouldParseMinimumValue() {
        // Given
        val goalString = "min(50)"

        // When
        val goal = goalParser.parse(goalString)

        // Then
        assertNotNull(goal)
        assertTrue(goal is MinGoal)
        assertEquals(50, (goal as MinGoal).value)
    }

    @Test
    @DisplayName("Parse as maximum value")
    fun shouldParseMaximumValue() {
        // Given
        val goalString = "max(13)"

        // When
        val goal = goalParser.parse(goalString)

        // Then
        assertNotNull(goal)
        assertTrue(goal is MaxGoal)
        assertEquals(13, (goal as MaxGoal).value)
    }

    // TODO Check what happens when format is wrong
}
