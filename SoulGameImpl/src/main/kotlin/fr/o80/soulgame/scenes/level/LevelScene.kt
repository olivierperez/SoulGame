package fr.o80.soulgame.scenes.level

import fr.o80.gamelib.Be
import fr.o80.gamelib.Scene
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.loop.MouseButtonPipeline
import fr.o80.gamelib.loop.MouseMovePipeline
import fr.o80.gamelib.loop.Window
import fr.o80.gamelib.menu.Menu
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.service.Services
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground
import fr.o80.soulgame.scenes.level.PlayingState.COUNTDOWN
import fr.o80.soulgame.scenes.level.PlayingState.PAUSE
import fr.o80.soulgame.scenes.level.PlayingState.PLAYING
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

    private lateinit var pauseMenu: Menu
    private lateinit var darkOverlay: PauseOverlayRenderer
    private lateinit var countDownRenderer: CountDownRenderer

    override fun open(
        window: Window,
        services: Services,
        keyPipeline: KeyPipeline,
        mouseButtonPipeline: MouseButtonPipeline,
        mouseMovePipeline: MouseMovePipeline
    ) {
        level = LevelLoader().load(levelName)

        loadEntities(level)

        score = Score()
        timing = Timing(initialMana)
        resources = LevelResources()
        resources.open()
        renderer = LevelRenderer(level, resources, window, tileSize)
        renderer.open()
        system = LevelSystem(knight, level, tileSize, resources, manaReloading, ::gameOver)
        system.open(keyPipeline)
        levelState = LevelState(level, mob, knight, score, timing, COUNTDOWN)

        keyPipeline.onKey(GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_PRESS) {
            levelState.playingState = when (levelState.playingState) {
                PLAYING -> PAUSE
                PAUSE -> PLAYING
                COUNTDOWN -> levelState.playingState
            }
        }

        pauseMenu = Menu.MenuBuilder()
            .of(
                top = .0,
                left = .0,
                right = window.width.toDouble(),
                bottom = window.height.toDouble()
            )
            .andResources(
                background = greenBackground,
                textResources = TextResources(
                    font = resource("fonts/LaserCutRegular.ttf"),
                    fontHeight = 50f
                ),
                titleResources = TextResources(
                    font = resource("fonts/LaserCutRegular.ttf"),
                    fontHeight = 99f
                )
            )
            .withPipelines(
                mouseButtonPipeline,
                mouseMovePipeline
            )
            .andLayout {
                title("Pause", verticalMargin = 50.0)
                button("Resume") {
                    levelState.playingState = PLAYING
                }
                button("Select level") {
                    sceneManager.openLevelSelector()
                }
                button("Quit") {
                    sceneManager.quit()
                }
            }
            .build()
        darkOverlay = PauseOverlayRenderer(window)
        countDownRenderer = CountDownRenderer(window)
        countDownRenderer.open()
    }

    override fun close() {
        resources.close()
        renderer.close()
        pauseMenu.close()
        countDownRenderer.close()
    }

    override suspend fun update() {
        Be exhaustive when (levelState.playingState) {
            PAUSE -> {
                pauseMenu.update()
            }
            PLAYING -> {
                renderer.update()
                system.update(levelState)
            }
            COUNTDOWN -> {
                countDownRenderer.update(levelState)
            }
        }
    }

    override suspend fun render() {
        Be exhaustive when (levelState.playingState) {
            PAUSE -> {
                renderer.render(levelState)
                darkOverlay.render()
                pauseMenu.render()
            }
            PLAYING -> {
                renderer.render(levelState)
            }
            COUNTDOWN -> {
                renderer.render(levelState)
                darkOverlay.render()
                countDownRenderer.render()
            }
        }
    }

    private fun gameOver(score: Long) {
        sceneManager.openGameOver(levelName, score)
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