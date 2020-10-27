package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.Scene
import fr.o80.gamelib.drawing.Image
import fr.o80.gamelib.drawing.Sprite
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.scenes.level.entity.Knight
import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.level.Level
import org.lwjgl.glfw.GLFW

private const val tileSize = 64f

class LevelScene(
    private val sceneManager: SoulSceneManager,
    private val levelName: String
) : Scene {

    private lateinit var levelState: LevelState

    private lateinit var knight: Knight
    private lateinit var mob: List<Soul>

    private lateinit var entitySprite: Sprite
    private lateinit var wallsSprite: Sprite
    private lateinit var extrasSprite: Sprite

    private lateinit var level: Level
    private lateinit var score: Score

    private lateinit var system: LevelSystem
    private lateinit var renderer: LevelRenderer

    override fun open(keyPipeline: KeyPipeline) {
        level = LevelLoader().load(levelName)

        loadTextures()
        loadEntities(level)

        score = Score()
        system = LevelSystem(knight, level, tileSize)
        system.open(keyPipeline)
        renderer = LevelRenderer(level, tileSize)
        renderer.open(wallsSprite, extrasSprite)
        levelState = LevelState(level, mob, knight, score)

        keyPipeline.onKey(GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_PRESS) { sceneManager.quit() }
    }

    override fun close() {
    }

    override suspend fun update() {
        system.update(levelState)
    }

    override suspend fun render() {
        renderer.render(levelState)
    }

    private fun loadTextures() {
        entitySprite = Sprite(
            image = Image("./resources/sprites/knights.png"),
            unitWidth = 57,
            unitHeight = 87,
            marginTop = 4,
            horizontalPadding = 21,
            verticalPadding = 21
        )
        wallsSprite = Sprite(
            image = Image("./resources/sprites/walls.png"),
            unitWidth = 70,
            unitHeight = 70
        )
        extrasSprite = Sprite(
            image = Image("./resources/sprites/extras.png"),
            unitWidth = 16,
            unitHeight = 16
        )
    }

    private fun loadEntities(level: Level) {
        val knightSpawn = level.knightSpawn.toPosition(tileSize)
        knight = Knight(
            x = knightSpawn.x,
            y = knightSpawn.y,
            size = 51f,
            sprite = entitySprite,
            speed = 6f
        )

        mob = level.mobSpawns.map { spawn ->
            val position = spawn.toPosition(tileSize)
            Soul(
                x = position.x,
                y = position.y,
                size = 51f,
                sprite = entitySprite,
                speed = 4f
            )
        }
    }

}