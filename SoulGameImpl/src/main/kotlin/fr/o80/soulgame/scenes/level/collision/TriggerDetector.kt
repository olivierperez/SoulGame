package fr.o80.soulgame.scenes.level.collision

import fr.o80.soulgame.scenes.level.entity.Entity
import fr.o80.soulgame.scenes.level.level.Block
import kotlin.math.abs

class TriggerDetector(
    private val tileSize: Float,
    private val onTrigger: (Block, Entity) -> Unit
) {

    fun update(blocks: List<Block>, entities: List<Entity>) {
        entities.forEach { entity ->
            blocks.forEach { block ->
                if (isTriggeredBy(block, entity)) {
                    onTrigger(block, entity)
                }
            }
        }
    }

    private fun isTriggeredBy(block: Block, entity: Entity): Boolean {
        if (block.canBeTriggeredBy(entity)) {
            val blockCenterX = block.x * tileSize + tileSize / 2
            val blockCenterY = block.y * tileSize + tileSize / 2

            return abs(blockCenterX - entity.x) < entity.speed &&
                   abs(blockCenterY - entity.y) < entity.speed
        }

        return false
    }

}
