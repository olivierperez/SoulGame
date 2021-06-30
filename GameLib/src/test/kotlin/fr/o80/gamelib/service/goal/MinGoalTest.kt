package fr.o80.gamelib.service.goal

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MinGoal")
internal class MinGoalTest {

    @Test
    @DisplayName("is completed")
    fun shouldBeCompleted() {
        // Given
        val minGoal = MinGoal(20L)

        // When + Then
        assertTrue(minGoal.check(25))
        assertTrue(minGoal.check(25L))
        assertTrue(minGoal.check(20L))
    }

    @Test
    @DisplayName("is not completed")
    fun shouldNotBeCompleted() {
        // Given
        val minGoal = MinGoal(20L)

        // When + Then
        assertFalse(minGoal.check(13))
        assertFalse(minGoal.check(19L))
        assertFalse(minGoal.check("toto"))
        assertFalse(minGoal.check(null))
    }

}