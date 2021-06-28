package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.drawing.Image
import fr.o80.gamelib.drawing.Sprite
import fr.o80.soulgame.resourceFile
import fr.o80.soulgame.scenes.level.level.SpritesConfig

class LevelResources(
    private val spritesConfig: SpritesConfig
) {

    lateinit var entitySprite: Sprite
        private set

    lateinit var wallsSprite: Sprite
        private set

    lateinit var extrasSprite: Sprite
        private set

    fun open() {
        loadTextures()
    }

    fun getEntityUnits(): Pair<Int, Int> =
        Pair(entitySprite.unitWidth, entitySprite.unitHeight)

    private fun loadTextures() {
        entitySprite = Sprite(
            image = Image(resourceFile(spritesConfig.characters)),
            unitWidth = 34,
            unitHeight = 54
        )
        wallsSprite = Sprite(
            image = Image(resourceFile(spritesConfig.walls)),
            unitWidth = 70,
            unitHeight = 70
        )
        extrasSprite = Sprite(
            image = Image(resourceFile(spritesConfig.doors)),
            unitWidth = 70,
            unitHeight = 70
        )
    }

    fun close() {
        // TODO Release des textures
    }
}
