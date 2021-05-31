package fr.o80.soulgame.scenes.level.movement.mob

import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.level.Terrain
import fr.o80.soulgame.scenes.level.movement.EntityMovementCalculator
import fr.o80.soulgame.scenes.level.movement.GlobalMovement
import fr.o80.soulgame.scenes.level.movement.Movement

class LowerSoulMovementCalculator(
    private val terrain: Terrain,
    private val tileSize: Float,
    private val globalMovement: GlobalMovement
) : EntityMovementCalculator<Soul> {

    override fun update(entity: Soul) {
        entity.movement = Movement.STANDING
    }

}
