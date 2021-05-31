package fr.o80.soulgame.scenes.level.movement.mob

import fr.o80.gamelib.Be
import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.entity.Team
import fr.o80.soulgame.scenes.level.level.Terrain
import fr.o80.soulgame.scenes.level.movement.EntityMovementCalculator
import fr.o80.soulgame.scenes.level.movement.GlobalMovement

class SoulMovementCalculator(
    terrain: Terrain,
    tileSize: Float,
    globalMovement: GlobalMovement
) : EntityMovementCalculator<Soul> {

    private val undecided = UndecidedSoulMovementCalculator(terrain, tileSize, globalMovement)
    private val upper = UpperSoulMovementCalculator(terrain, tileSize, globalMovement)
    private val lower = LowerSoulMovementCalculator(terrain, tileSize, globalMovement)

    override fun update(soul: Soul) {
        Be exhaustive when (soul.team) {
            Team.UNDECIDED -> undecided.update(soul)
            Team.UPPER -> upper.update(soul)
            Team.LOWER -> lower.update(soul)
        }
    }
}
