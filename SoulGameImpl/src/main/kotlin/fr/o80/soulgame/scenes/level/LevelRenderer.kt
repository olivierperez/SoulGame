package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.GG
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.scenes.greenBackground
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
    private val resources: LevelResources,
    tileSize: Float
) {

    private val entityDrawer: EntityDrawer = EntityDrawer(SpriteDrawer(10))
    private val levelDrawer: LevelDrawer = LevelDrawer(SpriteDrawer(10), tileSize)
    private val textRenderer: TextRenderer = TextRenderer("./resources/fonts/LaserCutRegular.ttf")
    private val hud: HUD = HUD(textRenderer)

    fun open() {
        levelDrawer
            .register(Wall::class, resources.wallsSprite, WallTileSelector(level))
            .register(Door::class, resources.extrasSprite, ExtraTileSelector())
        textRenderer.init()
    }

    fun close() {
        // TODO Release TextRenderer
    }

    fun render(state: LevelState) {
        GG.glBlendFunc(GG.GL_SRC_ALPHA, GG.GL_ONE_MINUS_SRC_ALPHA)
        draw {
            clear(greenBackground)
            levelDrawer.render(state.level)
            state.mob.forEach { entity -> entityDrawer.render(entity, resources.entitySprite) }
            entityDrawer.render(state.knight, resources.entitySprite)
            hud.render(state.score, state.timing)
        }
    }

}