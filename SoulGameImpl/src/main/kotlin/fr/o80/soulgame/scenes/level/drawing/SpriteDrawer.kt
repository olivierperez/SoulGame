package fr.o80.soulgame.scenes.level.drawing

import fr.o80.gamelib.GG
import fr.o80.gamelib.drawing.Sprite
import fr.o80.gamelib.dsl.Vertex2f
import fr.o80.gamelib.dsl.draw
import fr.o80.soulgame.scenes.level.movement.Direction
import fr.o80.soulgame.scenes.level.movement.Movement

class SpriteDrawer(
    private val ticksPerFrame: Int = 4,
    private val drawOutline: Boolean = false
) {

    // TODO réinventer ces 2 méthodes draw...
    // TODO En fait le SpriteDrawer pourrait être découpé en DynamicSpriteDrawer/StaticSpriteDrawer
    fun drawStatic(
        sprite: Sprite,
        tileX: Int,
        tileY: Int,
        drawingX: Float,
        drawingY: Float,
        drawingZoneWidth: Float,
        drawingZoneHeight: Float
    ) {
        val (unitTopLeft, unitBottomRight) = sprite.computeUnitSprite(x = tileX, y = tileY)
        val (outWidth, outHeight) = computeRatio(sprite, drawingZoneWidth, drawingZoneHeight)

        doDraw(sprite, unitTopLeft, unitBottomRight, drawingX, drawingY, outWidth, outHeight)
    }

    fun drawAnimated(
        sprite: Sprite,
        characterIndex: Int,
        direction: Direction,
        movement: Movement,
        x: Float,
        y: Float,
        drawingZoneWidth: Float,
        drawingZoneHeight: Float,
        ticks: Long
    ) {
        val (unitTopLeft, unitBottomRight) = sprite.computeUnitSprite(
            x = characterIndex * 3 + movement.computeIndex(ticks),
            y = direction.index
        )
        val (outWidth, outHeight) = computeRatio(sprite, drawingZoneWidth, drawingZoneHeight)

        doDraw(sprite, unitTopLeft, unitBottomRight, x - outWidth / 2f, y - outHeight / 2f, outWidth, outHeight)
    }

    private fun doDraw(
        sprite: Sprite,
        spriteTopLeft: Vertex2f,
        spriteBottomRight: Vertex2f,
        drawingX: Float,
        drawingY: Float,
        drawingWidth: Float,
        drawingHeight: Float
    ) {
        draw {
            texture2d {
                color(1f, 1f, 1f)
                GG.glBindTexture(GG.GL_TEXTURE_2D, sprite.image.id)

                GG.glBegin(GG.GL_QUADS)
                GG.glTexCoord2f(spriteTopLeft.x, spriteTopLeft.y)
                GG.glVertex2f(drawingX, drawingY)

                GG.glTexCoord2f(spriteBottomRight.x, spriteTopLeft.y)
                GG.glVertex2f(drawingX + drawingWidth, drawingY)

                GG.glTexCoord2f(spriteBottomRight.x, spriteBottomRight.y)
                GG.glVertex2f(drawingX + drawingWidth, drawingY + drawingHeight)

                GG.glTexCoord2f(spriteTopLeft.x, spriteBottomRight.y)
                GG.glVertex2f(drawingX, drawingY + drawingHeight)
                GG.glEnd()
            }

            doDrawOutline(drawingX, drawingY, drawingWidth, drawingHeight)
        }
    }

    private fun doDrawOutline(drawingX: Float, drawingY: Float, outWidth: Float, outHeight: Float) {
        if (drawOutline) {
            draw {
                lineWidth(1f)
                color(0f, 0f, 0f)
                rect(drawingX + 1, drawingY, drawingX + outWidth, drawingY + outHeight - 1)
            }
        }
    }

    private fun Movement.computeIndex(ticks: Long): Int {
        val delta = ticks / ticksPerFrame
        return this.indices[(delta % this.indices.size).toInt()]
    }

    private fun computeRatio(sprite: Sprite, desiredWidth: Float, desireHeight: Float): Pair<Float, Float> {
        val imageRatio = sprite.unitWidth.toFloat() / sprite.unitHeight.toFloat()

        return if (imageRatio > 1) {
            Pair(desiredWidth, desiredWidth / imageRatio)
        } else {
            Pair(desireHeight * imageRatio, desireHeight)
        }
    }

}
