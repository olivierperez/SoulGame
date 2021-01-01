package fr.o80.gamelib.loop

import fr.o80.gamelib.CursorManager
import fr.o80.gamelib.GG
import fr.o80.gamelib.Game
import fr.o80.gamelib.Scene
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.NativeType

class GameLoop(
    private val game: Game,
    private val width: Int,
    private val height: Int,
    private val updatesPerSecond: Int,
    private val windowName: String
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private lateinit var window: Window
    private lateinit var cursorManager: CursorManager

    private val mouseButtonPipeline = MouseButtonPipelineImpl()
    private val keyPipeline = KeyPipelineImpl()
    private val mouseMovePipeline = MouseMovePipelineImpl(mouseButtonPipeline)

    private var currentScene: Scene? = null

    suspend fun start(initialScene: Scene) {
        println("Starting game...")
        scope.launch {
            setup()
            debug()
            open(initialScene)
            loop()
        }.join()

        Callbacks.glfwFreeCallbacks(window.id)
        GLFW.glfwDestroyWindow(window.id)

        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

    private fun debug() {
        if (game.debug) {
            println("LWJGL version: " + Version.getVersion())
            println("OpenGL version: " + GG.glGetString(GG.GL_VERSION))
            println("Device: " + GG.glGetString(GG.GL_RENDERER))
        }
    }

    private fun setup() {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)

        window = Window(
            GLFW.glfwCreateWindow(width, height, windowName, MemoryUtil.NULL, MemoryUtil.NULL),
            width,
            height
        )
        if (window.id == MemoryUtil.NULL) {
            throw IllegalStateException("Failed to create window")
        }

        cursorManager = game.createCursorManager(window)

        GLFW.glfwSetKeyCallback(window.id, keyPipeline)
        GLFW.glfwSetMouseButtonCallback(window.id, mouseButtonPipeline)
        GLFW.glfwSetCursorPosCallback(window.id, mouseMovePipeline)

        MemoryStack.stackPush().use { stack ->
            val widthBuffer = stack.mallocInt(1)
            val heightBuffer = stack.mallocInt(1)

            GLFW.glfwGetWindowSize(window.id, widthBuffer, heightBuffer)

            val videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())!!

            GLFW.glfwSetWindowPos(
                window.id,
                (videoMode.width() - widthBuffer.get(0)) / 2,
                (videoMode.height() - heightBuffer.get(0)) / 2
            )
        }

        GLFW.glfwMakeContextCurrent(window.id)
        GLFW.glfwSwapInterval(1)

        GLFW.glfwShowWindow(window.id)

        GL.createCapabilities()

        GG.glMatrixMode(GG.GL_PROJECTION)

        ortho(Ortho.TOP_LEFT, GG.GL_PROJECTION)
        GG.glBlendFunc(GG.GL_SRC_ALPHA, GG.GL_ONE_MINUS_SRC_ALPHA)
    }

    private fun ortho(ortho: Ortho, @NativeType("GLenum") mode: Int = GG.GL_PROJECTION) {
        val width = width.toDouble()
        val height = height.toDouble()

        when (ortho) {
            Ortho.TOP_LEFT -> {
                GG.glOrtho(0.0, width, height, 0.0, 0.0, 1.0)
                GG.glMatrixMode(mode)
            }
            Ortho.BOTTOM_CENTER -> {
                GG.glOrtho(-width / 2, width / 2, 0.0, height, 0.0, 1.0)
                GG.glMatrixMode(mode)
            }
            Ortho.CENTER -> {
                GG.glOrtho(-width / 2, width / 2, -height / 2, height / 2, 0.0, 1.0)
                GG.glMatrixMode(mode)
            }
        }
    }

    private suspend fun loop() {
        var lastTime = GLFW.glfwGetTime()
        var timer = lastTime
        var delta = 0.0
        var now: Double
        var frames = 0
        var updates = 0
        val limitFPS = 1f / updatesPerSecond

        while (!GLFW.glfwWindowShouldClose(window.id)) {
            if (currentScene == null && game.debug) println("Not yet any scene !!!")
            now = GLFW.glfwGetTime()
            delta += (now - lastTime) / limitFPS

            lastTime = now

            while (delta > 1.0) {
                currentScene?.update()
                updates++
                delta--
            }

            GG.glClear(GG.GL_COLOR_BUFFER_BIT or GG.GL_DEPTH_BUFFER_BIT)
            currentScene?.render()
            GLFW.glfwPollEvents()
            GLFW.glfwSwapBuffers(window.id)
            frames++

            if (GLFW.glfwGetTime() - timer > 1) {
                timer++
                if (game.debug) {
                    println("FPS: $frames Updates: $updates")
                }
                frames = 0
                updates = 0
            }
        }
    }

    fun open(scene: Scene) {
        val oldScene = currentScene
        keyPipeline.clear()
        mouseButtonPipeline.clear()
        mouseMovePipeline.clear()
        scene.open(window, cursorManager, keyPipeline, mouseButtonPipeline, mouseMovePipeline)
        currentScene = scene
        oldScene?.close()
    }

    fun stop() {
        GLFW.glfwSetWindowShouldClose(window.id, true)
    }

}

enum class Ortho {
    TOP_LEFT,
    BOTTOM_CENTER,
    CENTER
}