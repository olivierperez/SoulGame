package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.Scene
import fr.o80.gamelib.loop.Dimension
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.scenes.level.entity.Knight
import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.level.Level
import org.lwjgl.glfw.GLFW

private const val tileSize = 64f
private const val initialMana = 2000
private const val manaReloading = 50

class LevelScene(
    private val sceneManager: SoulSceneManager,
    private val levelName: String
) : Scene {

    private lateinit var levelState: LevelState

    private lateinit var knight: Knight
    private lateinit var mob: List<Soul>

    private lateinit var level: Level
    private lateinit var score: Score
    private lateinit var timing: Timing

    private lateinit var resources: LevelResources
    private lateinit var system: LevelSystem
    private lateinit var renderer: LevelRenderer

    override fun open(keyPipeline: KeyPipeline, dimension: Dimension) {
        level = LevelLoader().load(levelName)

        loadEntities(level)

        score = Score()
        timing = Timing(initialMana)
        resources = LevelResources()
        resources.open()
        renderer = LevelRenderer(level, resources, dimension, tileSize)
        renderer.open()
        system = LevelSystem(knight, level, tileSize, resources, manaReloading, ::gameOver)
        system.open(keyPipeline)
        levelState = LevelState(level, mob, knight, score, timing)

        keyPipeline.onKey(GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_PRESS) { sceneManager.quit() }
    }

    override fun close() {
        resources.close()
        renderer.close()
    }

    override suspend fun update() {
        system.update(levelState)
    }

    override suspend fun render() {
        renderer.render(levelState)
    }

    private fun gameOver(score: Long) {
        sceneManager.openGameOver(score)
    }

    // TODO Move to LevelSystem ?
    private fun loadEntities(level: Level) {
        val knightSpawn = level.knightSpawn.toPosition(tileSize)
        knight = Knight(
            x = knightSpawn.x,
            y = knightSpawn.y,
            size = 51f,
            speed = 6f
        )

        mob = level.mobSpawns.map { spawn ->
            val position = spawn.toPosition(tileSize)
            Soul(
                x = position.x,
                y = position.y,
                size = 51f,
                speed = 4f
            )
        }
    }

}