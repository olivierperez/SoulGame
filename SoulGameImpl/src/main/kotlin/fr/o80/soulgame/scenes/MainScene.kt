package fr.o80.soulgame.scenes

import fr.o80.gamelib.Scene
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.soulgame.SoulSceneManager
import org.lwjgl.glfw.GLFW
import kotlin.random.Random

class MainScene(
    private val sceneManager: SoulSceneManager
) : Scene {

    override fun open(keyPipeline: KeyPipeline) {
        keyPipeline.onKey(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_RELEASE) {
            sceneManager.openGrayscaleScene(Random.nextFloat() % 1f)
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
            clear(0f, 0.2f, 0f)
            line(50f, 50f, 200f, 200f)
        }
    }
}