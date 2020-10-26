package fr.o80.soulgame.scenes.level.collision

import fr.o80.soulgame.scenes.level.entity.Entity
import kotlin.math.abs

class CollisionDetector(
    private val entity: Entity,
    private val onCollision: (Entity) -> Unit
) {

    private val collisionDistance: Float = 20f

    fun update(mob: List<Entity>) {
        mob.filter(::collisionWithEntity)
            .forEach(onCollision)
    }

    private fun collisionWithEntity(other: Entity): Boolean {
        return abs(entity.x - other.x) < collisionDistance &&
               abs(entity.y - other.y) < collisionDistance
    }

}
