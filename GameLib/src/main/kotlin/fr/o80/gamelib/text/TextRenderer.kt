package fr.o80.gamelib.text

import fr.o80.gamelib.GG
import fr.o80.gamelib.dsl.draw
import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBTTAlignedQuad
import org.lwjgl.stb.STBTTBakedChar
import org.lwjgl.stb.STBTTFontinfo
import org.lwjgl.stb.STBTruetype
import org.lwjgl.system.MemoryStack
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.IntBuffer
import kotlin.math.round

class TextRenderer(
    private val fontPath: String,
    private val margin: Float = 0f,
    private val fontHeight: Float = 20f
) {

    private val kerningEnabled = true
    private val drawBoxBorder = false

    private lateinit var ttf: ByteBuffer
    private lateinit var fontInfo: STBTTFontinfo

    private var ascent = 0
    private var descent = 0
    private var lineGap = 0

    private var bitmapWidth: Int = 0
    private var bitmapHeight: Int = 0
    private lateinit var charData: STBTTBakedChar.Buffer

    private val contentScaleX: Float = 1.0f
    private val contentScaleY: Float = 1.0f

    private val charBufferSize = 224

    private var textureId: Int = 0

    fun init() {
        try {
            ttf = IOUtil.ioResourceToByteBuffer(fontPath, 512 * 1024)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to load TTF!")
        }

        fontInfo = STBTTFontinfo.create()
        if (!STBTruetype.stbtt_InitFont(fontInfo, ttf)) {
            throw IllegalStateException("Failed to initialize font information!")
        }

        MemoryStack.stackPush().use { stack ->
            val pAscent = stack.mallocInt(1)
            val pDescent = stack.mallocInt(1)
            val pLineGap = stack.mallocInt(1)
            STBTruetype.stbtt_GetFontVMetrics(fontInfo, pAscent, pDescent, pLineGap)
            ascent = pAscent[0]
            descent = pDescent[0]
            lineGap = pLineGap[0]
        }

        bitmapWidth = round(512 * contentScaleX).toInt()
        bitmapHeight = round(512 * contentScaleY).toInt()
        val bitmap = BufferUtils.createByteBuffer(bitmapWidth * bitmapHeight)

        initFont(bitmap, bitmapWidth, bitmapHeight)
        initGl(bitmap)
    }

    fun render(text: String) {
        draw {
            texture2d {
                pushed {
                    translate(margin, margin + fontHeight, 0f)
                    renderText(text)
                }
            }
        }
    }

    fun getStringWidth(text: String): Float {
        return getStringWidth(fontInfo, text, 0, text.length)
    }

    fun getStringHeight(): Float {
        MemoryStack.stackPush().use { stack ->
            val bufAscent: IntBuffer = stack.ints(0)
            val bufDescent: IntBuffer = stack.ints(0)
            val bufLineGap: IntBuffer = stack.ints(0)

            STBTruetype.stbtt_GetFontVMetrics(fontInfo, bufAscent, bufDescent, bufLineGap)
            val scaleEm = STBTruetype.stbtt_ScaleForMappingEmToPixels(fontInfo, fontHeight)
            return (bufAscent.get(0) - bufDescent.get(0) + bufLineGap.get(0)) * scaleEm
        }
    }

    private fun initFont(bitmap: ByteBuffer, bitmapWidth: Int, bitmapHeight: Int) {
        charData = STBTTBakedChar.malloc(charBufferSize)

        STBTruetype.stbtt_BakeFontBitmap(
            ttf,
            fontHeight * contentScaleY,
            bitmap,
            bitmapWidth,
            bitmapHeight,
            32,
            charData
        )
    }

    private fun initGl(bitmap: ByteBuffer) {
        textureId = GG.glGenTextures()
        GG.glBindTexture(GG.GL_TEXTURE_2D, textureId)
        GG.glTexImage2D(
            GG.GL_TEXTURE_2D,
            0,
            GG.GL_ALPHA,
            this.bitmapWidth,
            this.bitmapHeight,
            0,
            GG.GL_ALPHA,
            GG.GL_UNSIGNED_BYTE,
            bitmap
        )
        GG.glTexParameteri(GG.GL_TEXTURE_2D, GG.GL_TEXTURE_MAG_FILTER, GG.GL_LINEAR)
        GG.glTexParameteri(GG.GL_TEXTURE_2D, GG.GL_TEXTURE_MIN_FILTER, GG.GL_LINEAR)
        GG.glBlendFunc(GG.GL_SRC_ALPHA, GG.GL_ONE_MINUS_SRC_ALPHA)
    }

    private fun renderText(text: String) {

        val scale = STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, fontHeight)

        MemoryStack.stackPush().use { stack ->
            val pCodePoints = stack.mallocInt(1)
            val x = stack.floats(0.0f)
            val y = stack.floats(0.0f)
            val q = STBTTAlignedQuad.mallocStack(stack)
            var lineStart = 0
            val factorX = 1.0f / contentScaleX
            val factorY = 1.0f / contentScaleY
            var lineY = 0.0f
            GG.glBindTexture(GG.GL_TEXTURE_2D, textureId)
            GG.glBegin(GG.GL_QUADS)
            var i = 0
            val to: Int = text.length
            while (i < to) {
                i += getCodePoint(text, to, i, pCodePoints)
                val codePoint = pCodePoints[0]
                if (codePoint == '\n'.toInt()) {
                    if (drawBoxBorder) {
                        GG.glEnd()
                        renderLineBB(lineStart, i - 1, y[0], scale, text)
                        GG.glBegin(GG.GL_QUADS)
                    }
                    y.put(0, y[0] + (ascent - descent + lineGap) * scale.also { lineY = it })
                    x.put(0, 0.0f)
                    lineStart = i
                    continue
                } else if (codePoint < 32 || 32 + charBufferSize <= codePoint) {
                    continue
                }
                val cpX = x[0]
                STBTruetype.stbtt_GetBakedQuad(
                    charData,
                    bitmapWidth,
                    bitmapHeight,
                    codePoint - 32,
                    x,
                    y,
                    q,
                    true
                )
                x.put(0, scale(cpX, x[0], factorX))
                if (kerningEnabled && i < to) {
                    getCodePoint(text, to, i, pCodePoints)
                    x.put(
                        0,
                        x[0] + STBTruetype.stbtt_GetCodepointKernAdvance(
                            fontInfo,
                            codePoint,
                            pCodePoints[0]
                        ) * scale
                    )
                }
                val x0: Float = scale(cpX, q.x0(), factorX)
                val x1: Float = scale(cpX, q.x1(), factorX)
                val y0: Float = scale(lineY, q.y0(), factorY)
                val y1: Float = scale(lineY, q.y1(), factorY)
                GG.glTexCoord2f(q.s0(), q.t0())
                GG.glVertex2f(x0, y0)
                GG.glTexCoord2f(q.s1(), q.t0())
                GG.glVertex2f(x1, y0)
                GG.glTexCoord2f(q.s1(), q.t1())
                GG.glVertex2f(x1, y1)
                GG.glTexCoord2f(q.s0(), q.t1())
                GG.glVertex2f(x0, y1)
            }
            GG.glEnd()
            if (drawBoxBorder) {
                renderLineBB(lineStart, text.length, lineY, scale, text)
            }
        }
    }

    private fun scale(center: Float, offset: Float, factor: Float): Float {
        return (offset - center) * factor + center
    }

    private fun renderLineBB(from: Int, to: Int, y: Float, scale: Float, text: String) {
        val adjustedY = y - descent * scale
        GG.glDisable(GG.GL_TEXTURE_2D)
        GG.glPolygonMode(GG.GL_FRONT, GG.GL_LINE)
        GG.glColor3f(1.0f, 1.0f, 0.0f)
        val width = getStringWidth(fontInfo, text, from, to)
        GG.glBegin(GG.GL_QUADS)
        GG.glVertex2f(0.0f, adjustedY)
        GG.glVertex2f(width, adjustedY)
        GG.glVertex2f(width, adjustedY - fontHeight)
        GG.glVertex2f(0.0f, adjustedY - fontHeight)
        GG.glEnd()
        GG.glEnable(GG.GL_TEXTURE_2D)
        GG.glPolygonMode(GG.GL_FRONT, GG.GL_FILL)
        GG.glColor3f(169f / 255f, 183f / 255f, 198f / 255f) // Text color
    }

    private fun getStringWidth(info: STBTTFontinfo, text: String, from: Int, to: Int): Float {
        var width = 0
        MemoryStack.stackPush().use { stack ->
            val pCodePoint = stack.mallocInt(1)
            val pAdvancedWidth = stack.mallocInt(1)
            val pLeftSideBearing = stack.mallocInt(1)
            var i = from
            while (i < to) {
                i += getCodePoint(text, to, i, pCodePoint)
                val cp = pCodePoint[0]
                STBTruetype.stbtt_GetCodepointHMetrics(info, cp, pAdvancedWidth, pLeftSideBearing)
                width += pAdvancedWidth[0]
                if (kerningEnabled && i < to) {
                    getCodePoint(text, to, i, pCodePoint)
                    width += STBTruetype.stbtt_GetCodepointKernAdvance(info, cp, pCodePoint[0])
                }
            }
        }
        return width * STBTruetype.stbtt_ScaleForPixelHeight(info, fontHeight)
    }

    private fun getCodePoint(text: String, to: Int, i: Int, cpOut: IntBuffer): Int {
        val c1 = text[i]
        if (Character.isHighSurrogate(c1) && i + 1 < to) {
            val c2 = text[i + 1]
            if (Character.isLowSurrogate(c2)) {
                cpOut.put(0, Character.toCodePoint(c1, c2))
                return 2
            }
        }
        cpOut.put(0, c1.toInt())
        return 1
    }

    fun close() {
        GG.glDeleteTextures(textureId)
    }
}
