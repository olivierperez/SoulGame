package fr.o80.soulgame.scenes.level.entity

import fr.o80.gamelib.Position
import fr.o80.soulgame.scenes.level.movement.Direction
import fr.o80.soulgame.scenes.level.movement.Movement

abstract class Entity(
    var x: Float,
    var y: Float,
    val size: Float,
    val speed: Float,
    var direction: Direction? = null,
    var movement: Movement = Movement.STANDING
) {

    abstract val characterIndex: Int

    fun respawn(spawnPosition: Position) {
        x = spawnPosition.x
        y = spawnPosition.y
    }

}
