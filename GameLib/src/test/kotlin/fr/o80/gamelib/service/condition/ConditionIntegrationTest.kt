package fr.o80.gamelib.service.condition

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ConditionIntegrationTest {

    private val conditionParser = ConditionParser()
    private val conditionResolver = ConditionResolver()

    @Test
    @DisplayName("Full scenario of condition Parsing + Resolving")
    fun parseAndResolve() {
        assertTrue(conditionResolver.resolve(conditionParser.parse("-789<=3"), emptyMap()))
        assertTrue(conditionResolver.resolve(conditionParser.parse("2<=3"), emptyMap()))
        assertTrue(conditionResolver.resolve(conditionParser.parse("3<=3"), emptyMap()))
        assertTrue(conditionResolver.resolve(conditionParser.parse("789>=-3"), emptyMap()))
        assertTrue(conditionResolver.resolve(conditionParser.parse("4>=3"), emptyMap()))
        assertTrue(conditionResolver.resolve(conditionParser.parse("3>=3"), emptyMap()))
        assertTrue(conditionResolver.resolve(conditionParser.parse("[mana]==0"), mapOf("mana" to 0)))
        assertTrue(conditionResolver.resolve(conditionParser.parse("[mana]!=0"), mapOf("mana" to -1)))
        assertTrue(conditionResolver.resolve(conditionParser.parse("[mana]<=0"), mapOf("mana" to 0)))
        assertTrue(conditionResolver.resolve(conditionParser.parse("[mana]<=0"), mapOf("mana" to -1)))

        assertFalse(conditionResolver.resolve(conditionParser.parse("-789>=3"), emptyMap()))
        assertFalse(conditionResolver.resolve(conditionParser.parse("2>=3"), emptyMap()))
        assertFalse(conditionResolver.resolve(conditionParser.parse("789<=-3"), emptyMap()))
        assertFalse(conditionResolver.resolve(conditionParser.parse("4<=3"), emptyMap()))
        assertFalse(conditionResolver.resolve(conditionParser.parse("[mana]==0"), mapOf("mana" to -1)))
        assertFalse(conditionResolver.resolve(conditionParser.parse("[mana]!=0"), mapOf("mana" to 0)))
        assertFalse(conditionResolver.resolve(conditionParser.parse("[mana]<=0"), mapOf("mana" to 2000)))
    }
}
