package fr.o80.gamelib.drawing

import fr.o80.gamelib.GG
import org.lwjgl.BufferUtils
import java.io.File
import javax.imageio.ImageIO

class Image(filename: String) {

    var id: Int
        private set
    val width: Int
    val height: Int

    init {
        val bufferedImage = ImageIO.read(File(filename))
        width = bufferedImage.width
        height = bufferedImage.height

        val raw = bufferedImage.getRGB(0, 0, width, height, null, 0, width)
        val pixels = BufferUtils.createByteBuffer(width * height * 4)

        for (i in 0 until width) {
            for (j in 0 until height) {
                val pixel = raw[i * height + j]
                pixels.put(pixel.red())
                pixels.put(pixel.green())
                pixels.put(pixel.blue())
                pixels.put(pixel.alpha())
            }
        }

        pixels.flip()

        id = GG.glGenTextures()
        GG.glBindTexture(GG.GL_TEXTURE_2D, id)
        GG.glTexParameteri(GG.GL_TEXTURE_2D, GG.GL_TEXTURE_MIN_FILTER, GG.GL_NEAREST)
        GG.glTexParameteri(GG.GL_TEXTURE_2D, GG.GL_TEXTURE_MAG_FILTER, GG.GL_NEAREST)
        GG.glTexImage2D(GG.GL_TEXTURE_2D, 0, GG.GL_RGBA, width, height, 0, GG.GL_RGBA, GG.GL_UNSIGNED_BYTE, pixels)
    }

    fun unload() {
        GG.glDeleteTextures(id)
        id = -1
    }
}