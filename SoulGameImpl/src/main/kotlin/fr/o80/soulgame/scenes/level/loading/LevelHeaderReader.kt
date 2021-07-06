package fr.o80.soulgame.scenes.level.loading

import fr.o80.gamelib.service.condition.ConditionParser
import fr.o80.gamelib.service.goal.Goal
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
    private val goalsString: MutableMap<String, String> = mutableMapOf()

    fun read(line: String) {
        val parts = line.trim().split('=', limit = 2)
        when {
            parts[0] == "Level.Name" -> levelName = parts[1]
            parts[0] == "Mana.GainAtPortal" -> manaGainAtPortal = parts[1]
            parts[0] == "Mana.GainAtConversion" -> manaGainAtConversion = parts[1]
            parts[0] == "Mana.Initial" -> manaInitial = parts[1]
            parts[0] == "Mana.Loss" -> manaLoss = parts[1]
            parts[0] == "Mana.Max" -> manaMax = parts[1]
            parts[0] == "Font.Path" -> font = parts[1]
            parts[0] == "Sprite.Characters" -> spriteCharacters = parts[1]
            parts[0] == "Sprite.Doors" -> spriteDoors = parts[1]
            parts[0] == "Sprite.Walls" -> spriteWalls = parts[1]
            parts[0] == "EndWhen" -> endWhen = parts[1]
            parts[0].startsWith("Goal.") -> goalsString[parts[0].removePrefix("Goal.")] = parts[1]
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
            endWhen = conditionParser.parse(requireString(endWhen, "EndWhen")),
            goals = goalsString.requireNotEmpty("Goal.").toGoals()
        )
    }

    private fun Map<String, String>.toGoals(): List<Goal> {
        return entries.map { (name, rule) ->
            Goal(
                name = name,
                condition = conditionParser.parse(rule)
            )
        }
    }
}

private fun requireString(value: String?, name: String): String {
    return value ?: throw MalformedLevelFile("$name is not set!")
}

private fun requireInt(value: String?, name: String): Int {
    return value?.toIntOrNull() ?: throw MalformedLevelFile("$name is not set, or not a number!")
}

private fun <K, V> Map<K, V>.requireNotEmpty(name: String): Map<K, V> {
    return this.takeIf { it.isNotEmpty() } ?: throw MalformedLevelFile("$name is not set!")
}
