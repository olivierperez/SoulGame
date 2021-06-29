package fr.o80.soulgame.scenes.level

import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import fr.o80.gamelib.service.condition.Condition
import fr.o80.gamelib.service.condition.IntValueOperand
import fr.o80.gamelib.service.condition.Operation
import fr.o80.gamelib.service.condition.ParamValueOperand
import fr.o80.soulgame.scenes.level.loading.LevelLoader
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class LevelLoaderTest {

    private val levelLoader = LevelLoader()

    private val manaLessOrEqualZero = Condition(
        ParamValueOperand("mana"),
        IntValueOperand(0),
        Operation.LTE
    )

    @Test
    @DisplayName("Load a level with all the required parameters")
    fun loadLevelWithAllRequiredParameters() {
        // Given
        val content = """
            |Level.Name=Testing
            |Mana.GainAtPortal=1
            |Mana.GainAtConversion=2
            |Mana.Initial=3
            |Mana.Loss=4
            |Mana.Max=5
            |Font.Path=LaFonte
            |Sprite.Characters=characters.webp
            |Sprite.Doors=doors.webp
            |Sprite.Walls=walls.webp
            |EndWhen=[mana]<=0
            |
            |#####
            |##A##
            |##+##
            |##:##
            |#####
        """.trimMargin()

        // When
        val level = levelLoader.load("level code", content.byteInputStream())

        // Then
        expect(level.settings.name).toBe("Testing")
        expect(level.settings.mana.gainAtPortal).toBe(1)
        expect(level.settings.mana.gainAtConversion).toBe(2)
        expect(level.settings.mana.initial).toBe(3)
        expect(level.settings.mana.loss).toBe(4)
        expect(level.settings.mana.max).toBe(5)
        expect(level.settings.endWhen).toBe(manaLessOrEqualZero)
        expect(level.settings.font).toBe("LaFonte")
        expect(level.settings.sprite.characters).toBe("characters.webp")
        expect(level.settings.sprite.doors).toBe("doors.webp")
        expect(level.settings.sprite.walls).toBe("walls.webp")
    }
}