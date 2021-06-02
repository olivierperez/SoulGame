package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.service.i18n.Messages
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground
import fr.o80.soulgame.scenes.level.drawing.EntityDrawer
import fr.o80.soulgame.scenes.level.drawing.ExtraTileSelector
import fr.o80.soulgame.scenes.level.drawing.HUD
import fr.o80.soulgame.scenes.level.drawing.SpriteDrawer
import fr.o80.soulgame.scenes.level.drawing.TerrainDrawer
import fr.o80.soulgame.scenes.level.drawing.selector.WallTileSelector
import fr.o80.soulgame.scenes.level.level.Door
import fr.o80.soulgame.scenes.level.level.Level
import fr.o80.soulgame.scenes.level.level.Wall

class LevelRenderer(
    private val level: Level,
    private val resources: LevelResources,
    window: Window,
    messages: Messages,
    tileSize: Float
) {

    private val entityDrawer: EntityDrawer = EntityDrawer(SpriteDrawer(4))
    private val terrainDrawer: TerrainDrawer = TerrainDrawer(SpriteDrawer(4), tileSize)
    private val textRenderer: TextRenderer = TextRenderer(resource(level.settings.font))
    private val hud: HUD = HUD(textRenderer, window, messages)
    private var ticks: Long = 0

    fun open() {
        terrainDrawer
            .register(Wall::class, resources.wallsSprite, WallTileSelector(level.terrain))
            .register(Door::class, resources.extrasSprite, ExtraTileSelector())
        textRenderer.init()
    }

    fun close() {
        textRenderer.close()
    }

    fun update() {
        ticks ++
    }

    fun render(state: LevelState) {
        draw {
            clear(greenBackground)
            terrainDrawer.render(state.level.terrain)
            state.mob.forEach { entity -> entityDrawer.render(entity, resources.entitySprite, ticks) }
            entityDrawer.render(state.knight, resources.entitySprite, ticks)
            hud.render(state.score, state.mana)
        }
    }

}