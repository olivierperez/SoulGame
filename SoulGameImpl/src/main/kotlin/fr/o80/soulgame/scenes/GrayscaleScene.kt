package fr.o80.soulgame.scenes

import fr.o80.gamelib.Scene
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.KeyPipeline
import fr.o80.soulgame.SoulSceneManager
import org.lwjgl.glfw.GLFW

class GrayscaleScene(
    private val sceneManager: SoulSceneManager,
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