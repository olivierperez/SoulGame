package fr.o80.soul.scenes

import fr.o80.soul.Scene
import fr.o80.soul.SceneManager
import fr.o80.soul.dsl.draw
import fr.o80.soul.loop.KeyPipeline
import org.lwjgl.glfw.GLFW

class GrayscaleScene(
    val sceneManager: SceneManager,
    private val scale: Float
) : Scene {

    override fun open(keyPipeline: KeyPipeline) {
        keyPipeline.onKey(GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_RELEASE) {
            sceneManager.quit()
        }
    }

    override fun close() {
    }

    override suspend fun update() {
    }

    override suspend fun render() {
        draw {
            clear(scale)
        }
    }
}