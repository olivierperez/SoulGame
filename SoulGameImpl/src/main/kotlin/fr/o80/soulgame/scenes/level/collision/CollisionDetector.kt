package fr.o80.soulgame.scenes.level.collision

import fr.o80.soulgame.scenes.level.LevelState
import fr.o80.soulgame.scenes.level.entity.Entity
import kotlin.math.abs

class CollisionDetector(
    private val entity: Entity,
    private val onCollision: (Entity, LevelState) -> Unit
) {

    private val collisionDistance: Float = 25f

    fun update(mob: List<Entity>, state: LevelState) {
        mob.filter(::collisionWithEntity)
            .forEach { entity -> onCollision(entity, state) }
    }

    private fun collisionWithEntity(other: Entity): Boolean {
        return abs(entity.x - other.x) < collisionDistance &&
               abs(entity.y - other.y) < collisionDistance
    }

}
