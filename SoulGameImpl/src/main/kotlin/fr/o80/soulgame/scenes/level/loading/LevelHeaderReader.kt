package fr.o80.soulgame.scenes.level.loading

import fr.o80.soulgame.scenes.level.level.LevelSettings
import fr.o80.soulgame.scenes.level.level.ManaConfig
import fr.o80.soulgame.scenes.level.level.SpritesConfig

class LevelHeaderReader(private val code: String) {

    private var levelName: String? = null
    private var manaGainAtPortal: Int? = null
    private var manaGainAtConversion: Int? = null
    private var manaInitial: Int? = null
    private var manaLoss: Int? = null
    private var manaMax: Int? = null
    private var font: String? = null
    private var spriteCharacters: String? = null
    private var spriteDoors: String? = null
    private var spriteWalls: String? = null

    fun read(line: String) {
        val parts = line.trim().split("=")
        when (parts[0]) {
            "Level.Name" -> levelName = parts[1]
            "Mana.GainAtPortal" -> manaGainAtPortal = parts[1].toInt()
            "Mana.GainAtConversion" -> manaGainAtConversion = parts[1].toInt()
            "Mana.Initial" -> manaInitial = parts[1].toInt()
            "Mana.Loss" -> manaLoss = parts[1].toInt()
            "Mana.Max" -> manaMax = parts[1].toInt()
            "Font.Path" -> font = parts[1]
            "Sprite.Characters" -> spriteCharacters = parts[1]
            "Sprite.Doors" -> spriteDoors = parts[1]
            "Sprite.Walls" -> spriteWalls = parts[1]
            else -> throw MalformedLevelFile("Unknown parameter '${parts[0]}'")
        }
    }

    fun build(): LevelSettings {
        return LevelSettings(
            code = code,
            name = levelName ?: throw MalformedLevelFile("Level.Name is not set!"),
            mana = ManaConfig(
                gainAtPortal = manaGainAtPortal ?: throw MalformedLevelFile("Mana.GainAtPortal is not set!"),
                gainAtConversion = manaGainAtConversion ?: throw MalformedLevelFile("Mana.GainAtConversion is not set!"),
                initial = manaInitial ?: throw MalformedLevelFile("Mana.Initial is not set!"),
                loss = manaLoss ?: throw MalformedLevelFile("Mana.Loss is not set!"),
                max = manaMax ?: throw MalformedLevelFile("Mana.Max is not set!"),
            ),
            font = font ?: throw MalformedLevelFile("Font.Path is not set!"),
            sprite = SpritesConfig(
                characters = spriteCharacters ?: throw MalformedLevelFile("Sprite.Characters is not set!"),
                doors = spriteDoors ?: throw MalformedLevelFile("Sprite.Doors is not set!"),
                walls = spriteWalls ?: throw MalformedLevelFile("Sprite.Walls is not set!"),
            )
        )
    }
}
