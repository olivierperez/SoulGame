package fr.o80.gamelib.service.condition

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ConditionResolverTest {

    private val conditionResolver = ConditionResolver()

    @Test
    @DisplayName("Resolve condition LessOrEqual with Int values")
    fun resolveLessOrEqualWithIntValues() {
        assertWithIntValues(Operation.LTE, expectedForLess = true, expectedForEquality = true, expectedForGreater = false)
    }

    @Test
    @DisplayName("Resolve condition GreaterOrEqual with Int values")
    fun resolveGreaterOrEqualWithIntValues() {
        assertWithIntValues(Operation.GTE, expectedForLess = false, expectedForEquality = true, expectedForGreater = true)
    }

    @Test
    @DisplayName("Resolve condition Equal with Int values")
    fun resolveEqualWithIntValues() {
        assertWithIntValues(Operation.EQUAL, expectedForLess = false, expectedForEquality = true, expectedForGreater = false)
    }

    @Test
    @DisplayName("Resolve condition Different with Int values")
    fun resolveDifferentWithIntValues() {
        assertWithIntValues(Operation.DIFFERENT, expectedForLess = true, expectedForEquality = false, expectedForGreater = true)
    }

    @Test
    @DisplayName("Resolve condition LTE with parameter values")
    fun resolveLessOrEqualWithParamValues() {
        // Given
        val condition = Condition(
            ParamValueOperand("Blackazu"),
            ParamValueOperand("tinybeak"),
            Operation.LTE
        )
        val params = mapOf(
            "Blackazu" to 8L,
            "tinybeak" to 12L
        )

        // When
        val resolve = conditionResolver.resolve(condition, params)

        // Then
        assertTrue(resolve)
    }

    private fun assertWithIntValues(
        operation: Operation,
        expectedForLess: Boolean,
        expectedForEquality: Boolean,
        expectedForGreater: Boolean
    ) {
        // Given
        val condition1 = Condition(
            LongValueOperand(4),
            LongValueOperand(5),
            operation
        )
        val condition2 = Condition(
            LongValueOperand(8),
            LongValueOperand(8),
            operation
        )
        val condition3 = Condition(
            LongValueOperand(8),
            LongValueOperand(7),
            operation
        )

        // When
        val resolve1 = conditionResolver.resolve(condition1, emptyMap())
        val resolve2 = conditionResolver.resolve(condition2, emptyMap())
        val resolve3 = conditionResolver.resolve(condition3, emptyMap())

        // Then
        assertEquals(expectedForLess, resolve1)
        assertEquals(expectedForEquality, resolve2)
        assertEquals(expectedForGreater, resolve3)
    }
}