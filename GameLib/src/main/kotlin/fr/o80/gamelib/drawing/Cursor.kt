package fr.o80.gamelib.drawing

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWImage
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Cursor(
    file: File,
    hotspotX: Int,
    hotspotY: Int
) {
    var id: Long
        private set
    val width: Int
    val height: Int

    init {
        val image: BufferedImage = ImageIO.read(file)
        width = image.width
        height = image.height

        val raw = image.getRGB(0, 0, width, height, IntArray(width * height), 0, width)
        val buffer = BufferUtils.createByteBuffer(width * height * 4)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = raw[y * width + x]
                buffer.put((pixel shr 16 and 0xFF).toByte()) // red
                buffer.put((pixel shr 8 and 0xFF).toByte()) // green
                buffer.put((pixel and 0xFF).toByte()) // blue
                buffer.put((pixel shr 24 and 0xFF).toByte()) // alpha
            }
        }
        buffer.flip() // this will flip the cursor image vertically

        val cursorImg = GLFWImage.create()
        cursorImg.width(width)
        cursorImg.height(height)
        cursorImg.pixels(buffer)

        id = GLFW.glfwCreateCursor(cursorImg, hotspotX, hotspotY)
    }

    fun unload() {
        GLFW.glfwDestroyCursor(id)
        id = -1
    }
}
