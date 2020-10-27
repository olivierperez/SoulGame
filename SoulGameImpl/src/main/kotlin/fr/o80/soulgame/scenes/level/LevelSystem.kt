package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.soulgame.scenes.level.collision.CollisionDetector
import fr.o80.soulgame.scenes.level.collision.TriggerDetector
import fr.o80.soulgame.scenes.level.entity.Entity
import fr.o80.soulgame.scenes.level.entity.Knight
import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.entity.Team
import fr.o80.soulgame.scenes.level.level.Block
import fr.o80.soulgame.scenes.level.level.Door
import fr.o80.soulgame.scenes.level.level.Level
import fr.o80.soulgame.scenes.level.movement.Direction
import fr.o80.soulgame.scenes.level.movement.GlobalMovement
import fr.o80.soulgame.scenes.level.movement.KnightMovementCalculator
import fr.o80.soulgame.scenes.level.movement.Movement
import fr.o80.soulgame.scenes.level.movement.mob.SoulMovementCalculator
import org.lwjgl.glfw.GLFW

class LevelSystem(
    private val knight: Knight,
    private val level: Level,
    private val tileSize: Float,
    private val resources: LevelResources
) {

    private lateinit var playerCollisionDetector: CollisionDetector
    private lateinit var triggerDetector: TriggerDetector

    private lateinit var knightMovementCalculator: KnightMovementCalculator
    private lateinit var soulMovementCalculator: SoulMovementCalculator

    fun open(keyPipeline: KeyPipeline) {
        playerCollisionDetector = CollisionDetector(knight) { entity -> (entity as? Soul)?.team = Team.UPPER }
        triggerDetector = TriggerDetector(tileSize)
        initMovementCalculators()
        listenKeys(keyPipeline)
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

    private fun initMovementCalculators() {
        val globalMovement = GlobalMovement(tileSize, resources.getEntityUnits())
        knightMovementCalculator = KnightMovementCalculator(
            level = level,
            tileSize = tileSize,
            adjustmentTolerance = 0.5f,
            globalMovement = globalMovement
        )
        soulMovementCalculator = SoulMovementCalculator(
            level = level,
            tileSize = tileSize,
            globalMovement = globalMovement
        )
    }

    private fun listenKeys(keyPipeline: KeyPipeline) {
        keyPipeline.onKey(GLFW.GLFW_KEY_S, GLFW.GLFW_PRESS) { knightMovementCalculator.pressedKey(Direction.DOWN) }
        keyPipeline.onKey(GLFW.GLFW_KEY_S, GLFW.GLFW_RELEASE) { knightMovementCalculator.releasedKey(Direction.DOWN) }
        keyPipeline.onKey(GLFW.GLFW_KEY_W, GLFW.GLFW_PRESS) { knightMovementCalculator.pressedKey(Direction.UP) }
        keyPipeline.onKey(GLFW.GLFW_KEY_W, GLFW.GLFW_RELEASE) { knightMovementCalculator.releasedKey(Direction.UP) }
        keyPipeline.onKey(GLFW.GLFW_KEY_D, GLFW.GLFW_PRESS) { knightMovementCalculator.pressedKey(Direction.RIGHT) }
        keyPipeline.onKey(GLFW.GLFW_KEY_D, GLFW.GLFW_RELEASE) { knightMovementCalculator.releasedKey(Direction.RIGHT) }
        keyPipeline.onKey(GLFW.GLFW_KEY_A, GLFW.GLFW_PRESS) { knightMovementCalculator.pressedKey(Direction.LEFT) }
        keyPipeline.onKey(GLFW.GLFW_KEY_A, GLFW.GLFW_RELEASE) { knightMovementCalculator.releasedKey(Direction.LEFT) }
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