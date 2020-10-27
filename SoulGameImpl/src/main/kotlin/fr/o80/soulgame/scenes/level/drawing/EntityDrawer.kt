package fr.o80.soulgame.scenes.level.drawing

import fr.o80.soulgame.scenes.level.entity.Entity
import fr.o80.soulgame.scenes.level.movement.Direction

class EntityDrawer(
    private val spriteDrawer: SpriteDrawer
) {
    // TODO Le sprite devrait être passé en params
    fun render(entity: Entity) {
        spriteDrawer.drawAnimated(
            sprite = entity.sprite,
            characterIndex = entity.characterIndex,
            direction = entity.direction ?: Direction.DOWN,
            movement = entity.movement,
            x = entity.x,
            y = entity.y,
            drawingZoneWidth = entity.size,
            drawingZoneHeight = entity.size
        )
    }
}