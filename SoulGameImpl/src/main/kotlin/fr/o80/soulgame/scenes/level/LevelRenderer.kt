package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.GG
import fr.o80.gamelib.drawing.Sprite
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.scenes.level.drawing.EntityDrawer
import fr.o80.soulgame.scenes.level.drawing.ExtraTileSelector
import fr.o80.soulgame.scenes.level.drawing.HUD
import fr.o80.soulgame.scenes.level.drawing.LevelDrawer
import fr.o80.soulgame.scenes.level.drawing.SpriteDrawer
import fr.o80.soulgame.scenes.level.drawing.selector.WallTileSelector
import fr.o80.soulgame.scenes.level.level.Door
import fr.o80.soulgame.scenes.level.level.Level
import fr.o80.soulgame.scenes.level.level.Wall

class LevelRenderer(
    private val level: Level,
    private val tileSize: Float
) {

    private lateinit var hud: HUD
    private lateinit var entityDrawer: EntityDrawer
    private lateinit var levelDrawer: LevelDrawer
    private lateinit var spriteDrawer: SpriteDrawer
    private lateinit var textRenderer: TextRenderer

    fun open(wallsSprite: Sprite, extrasSprite: Sprite) {
        loadDrawers(level, wallsSprite, extrasSprite)
    }

    fun render(state: LevelState) {
        GG.glBlendFunc(GG.GL_SRC_ALPHA, GG.GL_ONE_MINUS_SRC_ALPHA)
        draw {
            clear(0.2f, 0.4f, 0.2f)
            levelDrawer.render(state.level)
            state.mob.forEach { entity -> entityDrawer.render(entity) }
            entityDrawer.render(state.knight)
            hud.render(state.score)
        }
    }

    private fun loadDrawers(level: Level, wallsSprite: Sprite, extrasSprite: Sprite) {
        spriteDrawer = SpriteDrawer(10)
        entityDrawer = EntityDrawer(spriteDrawer)
        levelDrawer = LevelDrawer(spriteDrawer, tileSize)
            .register(Wall::class, wallsSprite, WallTileSelector(level))
            .register(Door::class, extrasSprite, ExtraTileSelector())
        textRenderer = TextRenderer("./resources/fonts/LaserCutRegular.ttf").apply { init() }
        hud = HUD(textRenderer)
    }
}