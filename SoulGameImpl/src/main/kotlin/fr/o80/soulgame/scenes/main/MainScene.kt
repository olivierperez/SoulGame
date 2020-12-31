package fr.o80.soulgame.scenes.main

import fr.o80.gamelib.Scene
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Dimension
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.gamelib.text.TextRenderer
import fr.o80.soulgame.SoulSceneManager
import fr.o80.soulgame.resource
import fr.o80.soulgame.scenes.greenBackground
import org.lwjgl.glfw.GLFW

class MainScene(
    private val sceneManager: SoulSceneManager
) : Scene {

    private val fontHeight: Float = 50f

    private val textRenderer: TextRenderer by lazy {
        println("Lazy textRenderer ${Thread.currentThread().id} - ${Thread.currentThread().name}")
        TextRenderer(
            fontPath = resource("fonts/LaserCutRegular.ttf"),
            margin = 0f,
            fontHeight = fontHeight
        )
    }

    override fun open(keyPipeline: KeyPipeline, dimension: Dimension) {
        keyPipeline.onKey(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_RELEASE) {
            sceneManager.openLevel("level_1")
        }
        // Load resources
        textRenderer.init()
    }

    override fun close() {
        // Unload resources
    }

    override suspend fun update() {

    }

    override suspend fun render() {
        draw {
            clear(greenBackground)

            textRenderer.render("Main screen, press \"space\" to continue!")
        }
    }
}