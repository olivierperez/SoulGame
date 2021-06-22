package fr.o80.gamelib.service.condition

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ConditionParserTest {

    private val conditionParser = ConditionParser()

    @Test
    @DisplayName("Parse a LessOrEqualThan condition with simple values")
    fun simpleLessOrEqual() {
        // Given
        val conditionString = "4<=5"

        // When
        val condition = conditionParser.parse(conditionString)

        // Then
        assertEquals(
            Condition(
                IntValueOperand(4),
                IntValueOperand(5),
                Operation.LTE
            ),
            condition
        )
    }

    @Test
    @DisplayName("Parse a GreaterOrEqualThan condition with simple values")
    fun simpleGreaterOrEqual() {
        // Given
        val conditionString = "49>=-5"

        // When
        val condition = conditionParser.parse(conditionString)

        // Then
        assertEquals(
            Condition(
                IntValueOperand(49),
                IntValueOperand(-5),
                Operation.GTE
            ),
            condition
        )
    }

    @Test
    @DisplayName("Parse a EqualTo condition with simple values")
    fun simpleEqualTo() {
        // Given
        val conditionString1 = "1=0"
        val conditionString2 = "1==0"

        // When
        val condition1 = conditionParser.parse(conditionString1)
        val condition2 = conditionParser.parse(conditionString2)

        // Then
        assertEquals(
            Condition(
                IntValueOperand(1),
                IntValueOperand(0),
                Operation.EQUAL
            ),
            condition1
        )
        assertEquals(condition1, condition2)
    }

    @Test
    @DisplayName("Parse a DifferentOf condition with simple values")
    fun simpleDifferentOf() {
        // Given
        val conditionString1 = "1<>0"
        val conditionString2 = "1!=0"

        // When
        val condition1 = conditionParser.parse(conditionString1)
        val condition2 = conditionParser.parse(conditionString2)

        // Then
        assertEquals(
            Condition(
                IntValueOperand(1),
                IntValueOperand(0),
                Operation.DIFFERENT
            ),
            condition1
        )
        assertEquals(condition1, condition2)
    }

    @Test
    @DisplayName("Parse a condition with a parameter value")
    fun parameterValue() {
        // Given
        val conditionString = "[mana]<=[time]"

        // When
        val condition = conditionParser.parse(conditionString)

        // Then
        assertEquals(
            Condition(
                ParamValueOperand("mana"),
                ParamValueOperand("time"),
                Operation.LTE
            ),
            condition
        )
    }
}