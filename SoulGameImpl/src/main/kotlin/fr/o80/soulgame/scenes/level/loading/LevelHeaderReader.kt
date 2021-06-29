package fr.o80.soulgame.scenes.level.loading

import fr.o80.gamelib.service.condition.ConditionParser
import fr.o80.soulgame.scenes.level.level.LevelSettings
import fr.o80.soulgame.scenes.level.level.ManaConfig
import fr.o80.soulgame.scenes.level.level.SpritesConfig

class LevelHeaderReader(
    private val code: Int,
    private val conditionParser: ConditionParser = ConditionParser()
) {

    private var levelName: String? = null
    private var manaGainAtPortal: String? = null
    private var manaGainAtConversion: String? = null
    private var manaInitial: String? = null
    private var manaLoss: String? = null
    private var manaMax: String? = null
    private var font: String? = null
    private var spriteCharacters: String? = null
    private var spriteDoors: String? = null
    private var spriteWalls: String? = null
    private var endWhen: String? = null

    fun read(line: String) {
        val parts = line.trim().split('=', limit = 2)
        when (parts[0]) {
            "Level.Name" -> levelName = parts[1]
            "Mana.GainAtPortal" -> manaGainAtPortal = parts[1]
            "Mana.GainAtConversion" -> manaGainAtConversion = parts[1]
            "Mana.Initial" -> manaInitial = parts[1]
            "Mana.Loss" -> manaLoss = parts[1]
            "Mana.Max" -> manaMax = parts[1]
            "Font.Path" -> font = parts[1]
            "Sprite.Characters" -> spriteCharacters = parts[1]
            "Sprite.Doors" -> spriteDoors = parts[1]
            "Sprite.Walls" -> spriteWalls = parts[1]
            "EndWhen" -> endWhen = parts[1]
            else -> throw MalformedLevelFile("Unknown parameter '${parts[0]}'")
        }
    }

    fun build(): LevelSettings {
        return LevelSettings(
            code = code,
            name = requireString(levelName, "Level.Name"),
            mana = ManaConfig(
                gainAtPortal = requireInt(manaGainAtPortal, "Mana.GainAtPortal"),
                gainAtConversion = requireInt(manaGainAtConversion, "Mana.GainAtConversion"),
                initial = requireInt(manaInitial, "Mana.Initial"),
                loss = requireInt(manaLoss, "Mana.Loss"),
                max = requireInt(manaMax, "Mana.Max"),
            ),
            font = font ?: throw MalformedLevelFile("Font.Path is not set!"),
            sprite = SpritesConfig(
                characters = requireString(spriteCharacters, "Sprite.Characters"),
                doors = requireString(spriteDoors, "Sprite.Doors"),
                walls = requireString(spriteWalls, "Sprite.Walls"),
            ),
            endWhen = conditionParser.parse(requireString(endWhen, "EndWhen"))
        )
    }

    private fun requireString(value: String?, name: String): String {
        return value ?: throw MalformedLevelFile("$name is not set!")
    }

    private fun requireInt(value: String?, name: String): Int {
        return value?.toIntOrNull() ?: throw MalformedLevelFile("$name is not set, or not a number!")
    }
}
