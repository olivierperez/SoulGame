package fr.o80.soulgame.scenes.level

import fr.o80.soulgame.scenes.level.collision.CollisionDetector
import fr.o80.soulgame.scenes.level.collision.TriggerDetector
import fr.o80.soulgame.scenes.level.entity.Entity
import fr.o80.soulgame.scenes.level.entity.Knight
import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.entity.Team
import fr.o80.soulgame.scenes.level.level.Block
import fr.o80.soulgame.scenes.level.level.Door
import fr.o80.soulgame.scenes.level.level.Level
import fr.o80.soulgame.scenes.level.movement.KnightMovementCalculator
import fr.o80.soulgame.scenes.level.movement.Movement
import fr.o80.soulgame.scenes.level.movement.mob.SoulMovementCalculator

class LevelSystem(
    private val knightMovementCalculator: KnightMovementCalculator,
    private val soulMovementCalculator: SoulMovementCalculator,
    private val knight: Knight,
    private val level: Level,
    private val tileSize: Float
) {

    private lateinit var playerCollisionDetector: CollisionDetector
    private lateinit var triggerDetector: TriggerDetector

    fun open() {
        playerCollisionDetector = CollisionDetector(knight) { entity -> (entity as? Soul)?.team = Team.UPPER }
        triggerDetector = TriggerDetector(tileSize)
    }

    fun update(state: LevelState) {
        knightMovementCalculator.update(state.knight)
        state.mob.forEach { soul ->
            soulMovementCalculator.update(soul)
            triggerDetector.update(state.level.blocks, state.mob + state.knight) { block: Block, entity: Entity ->
                onTrigger(block, entity, state.score)
            }
        }
        playerCollisionDetector.update(state.mob)
    }

    private fun onTrigger(block: Block, entity: Entity, score: Score) {
        if (block is Door && entity is Soul && entity.team == Team.UPPER) {
            // TODO Particules

            score.increase()

            entity.team = Team.UNDECIDED
            entity.movement = Movement.STANDING
            entity.direction = null
            entity.respawn(level.mobSpawns.random().toPosition(tileSize))
        }
    }
}