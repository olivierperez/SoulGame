package fr.o80.soulgame.scenes.level.drawing

import fr.o80.gamelib.drawing.Sprite
import fr.o80.soulgame.scenes.level.entity.Entity
import fr.o80.soulgame.scenes.level.movement.Direction

class EntityDrawer(
    private val spriteDrawer: SpriteDrawer
) {
    fun render(entity: Entity, sprite: Sprite, ticks: Long) {
        spriteDrawer.drawAnimated(
            sprite = sprite,
            characterIndex = entity.characterIndex,
            direction = entity.direction ?: Direction.DOWN,
            movement = entity.movement,
            x = entity.x,
            y = entity.y,
            ticks = ticks
        )
    }
}