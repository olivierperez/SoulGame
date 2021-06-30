package fr.o80.gamelib.service.goal

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MaxGoal")
internal class MaxGoalTest {

    @Test
    @DisplayName("is completed")
    fun shouldBeCompleted() {
        // Given
        val maxGoal = MaxGoal(20L)

        // When + Then
        assertTrue(maxGoal.check(19))
        assertTrue(maxGoal.check(0L))
        assertTrue(maxGoal.check(20L))
    }

    @Test
    @DisplayName("is not completed")
    fun shouldNotBeCompleted() {
        // Given
        val maxGoal = MaxGoal(20L)

        // When + Then
        assertFalse(maxGoal.check(21))
        assertFalse(maxGoal.check(99L))
        assertFalse(maxGoal.check("toto"))
        assertFalse(maxGoal.check(null))
    }

}