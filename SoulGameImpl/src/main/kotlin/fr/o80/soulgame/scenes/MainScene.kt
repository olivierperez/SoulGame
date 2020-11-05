package fr.o80.soulgame.scenes

import fr.o80.gamelib.Scene
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.Dimension
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.soulgame.SoulSceneManager
import org.lwjgl.glfw.GLFW

class MainScene(
    private val sceneManager: SoulSceneManager
) : Scene {

    override fun open(keyPipeline: KeyPipeline, dimension: Dimension) {
        keyPipeline.onKey(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_RELEASE) {
            sceneManager.openLevel("level_1")
        }
        // Load resources
    }

    override fun close() {
        // Unload resources
    }

    override suspend fun update() {

    }

    override suspend fun render() {
        draw {
            clear(greenBackground)
            line(50f, 50f, 200f, 200f)
        }
    }
}