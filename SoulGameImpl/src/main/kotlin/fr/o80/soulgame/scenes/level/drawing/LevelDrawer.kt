package fr.o80.soulgame.scenes.level.drawing

import fr.o80.gamelib.drawing.Sprite
import fr.o80.gamelib.dsl.draw
import fr.o80.soulgame.scenes.level.drawing.selector.TileSelector
import fr.o80.soulgame.scenes.level.level.Block
import fr.o80.soulgame.scenes.level.level.Level
import kotlin.reflect.KClass

class LevelDrawer(
    private val spriteDrawer: SpriteDrawer,
    private val tileSize: Float
) {

    private val blocksInfo: MutableMap<KClass<out Block>, Pair<Sprite, TileSelector>> = mutableMapOf()

    fun register(kclass: KClass<out Block>, sprite: Sprite, tileSelector: TileSelector): LevelDrawer {
        blocksInfo[kclass] = Pair(sprite, tileSelector)
        return this
    }

    fun render(level: Level) {
        draw {
            texture2d {
                level.blocks.forEach { block ->
                    val x = block.x
                    val y = block.y

                    blocksInfo[block::class]?.let { (sprite, tileSelector) ->
                        val (tileX, tileY) = tileSelector.get(block, x, y)
                        spriteDrawer.drawStatic(
                            sprite = sprite,
                            tileX = tileX,
                            tileY = tileY,
                            drawingX = x * tileSize,
                            drawingY = y * tileSize,
                            drawingZoneHeight = tileSize,
                            drawingZoneWidth = tileSize
                        )
                    }

                }
            }
        }
    }

}
