package fr.o80.gamelib.dsl

import fr.o80.gamelib.GG
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@DslMarker
annotation class Drawer

@Drawer
inline fun draw(block: Draw.() -> Unit) {
    Draw().apply(block)
}

@Drawer
class Draw {

    @Drawer
    fun clear(red: Float, green: Float, blue: Float, alpha: Float = 1f) {
        GG.glClearColor(red, green, blue, alpha)
    }

    @Drawer
    fun clear(color: Vertex3f) {
        GG.glClearColor(color.x, color.y, color.z, 1f)
    }

    @Drawer
    fun clear(grey: Float) {
        GG.glClearColor(grey, grey, grey, 1f)
    }

    @Drawer
    inline fun pushed(block: Draw.() -> Unit) {
        GG.glPushMatrix()
        block()
        GG.glPopMatrix()
    }

    @Drawer
    fun line(from: Vertex3f, to: Vertex3f) {
        GG.glBegin(GG.GL_LINES)
        GG.glVertex3f(from.x, from.y, from.z)
        GG.glVertex3f(to.x, to.y, to.z)
        GG.glEnd()
    }

    @Drawer
    fun line(x1: Float, y1: Float, x2: Float, y2: Float) {
        GG.glBegin(GG.GL_LINES)
        GG.glVertex2f(x1, y1)
        GG.glVertex2f(x2, y2)
        GG.glEnd()
    }

    @Drawer
    fun quad(a: Vertex3f, b: Vertex3f, c: Vertex3f, d: Vertex3f) {
        GG.glBegin(GG.GL_QUADS)
        GG.glVertex3f(a.x, a.y, a.z)
        GG.glVertex3f(b.x, b.y, b.z)
        GG.glVertex3f(c.x, c.y, c.z)
        GG.glVertex3f(d.x, d.y, d.z)
        GG.glEnd()
    }

    @Drawer
    fun quad(a: Vertex3d, b: Vertex3d, c: Vertex3d, d: Vertex3d) {
        GG.glBegin(GG.GL_QUADS)
        GG.glVertex3d(a.x, a.y, a.z)
        GG.glVertex3d(b.x, b.y, b.z)
        GG.glVertex3d(c.x, c.y, c.z)
        GG.glVertex3d(d.x, d.y, d.z)
        GG.glEnd()
    }

    @Drawer
    fun quad(x1: Float, y1: Float, x2: Float, y2: Float) {
        quad(
            Vertex3f(x1, y1, 0f),
            Vertex3f(x2, y1, 0f),
            Vertex3f(x2, y2, 0f),
            Vertex3f(x1, y2, 0f),
        )
    }

    @Drawer
    fun quad(x1: Double, y1: Double, x2: Double, y2: Double) {
        quad(
            Vertex3d(x1, y1, .0),
            Vertex3d(x2, y1, .0),
            Vertex3d(x2, y2, .0),
            Vertex3d(x1, y2, .0),
        )
    }

    @Drawer
    fun translate(x: Float, y: Float, z: Float) {
        GG.glTranslatef(x, y, z)
    }

    @Drawer
    fun translate(x: Double, y: Double, z: Double) {
        GG.glTranslated(x, y, z)
    }

    @Drawer
    fun rotate(angle: Float, x: Float, y: Float, z: Float) {
        GG.glRotatef(angle, x, y, z)
    }

    @Drawer
    fun color(red: Float, green: Float, blue: Float) {
        GG.glColor3f(red, green, blue)
    }

    @Drawer
    fun color(red: Float, green: Float, blue: Float, alpha: Float) {
        GG.glColor4f(red, green, blue, alpha)
    }

    @Drawer
    fun color(color: Vertex3f) {
        GG.glColor3f(color.x, color.y, color.z)
    }

    @Drawer
    fun lineWidth(width: Float) {
        GG.glLineWidth(width)
    }

    @Drawer
    fun point(x: Double, y: Double, z: Double) {
        GG.glBegin(GG.GL_POINTS)
        GG.glVertex3d(x, y, z)
        GG.glEnd()
    }

    @Drawer
    fun rect(x1: Float, y1: Float, x2: Float, y2: Float) {
        GG.glBegin(GG.GL_LINES)
        GG.glVertex2f(x1, y1)
        GG.glVertex2f(x2, y1)
        GG.glVertex2f(x2, y1)
        GG.glVertex2f(x2, y2)
        GG.glVertex2f(x2, y2)
        GG.glVertex2f(x1, y2)
        GG.glVertex2f(x1, y2)
        GG.glVertex2f(x1, y1)
        GG.glEnd()
    }

    @Drawer
    fun rect(x1: Double, y1: Double, x2: Double, y2: Double) {
        GG.glBegin(GG.GL_LINES)
        GG.glVertex2d(x1, y1)
        GG.glVertex2d(x2, y1)
        GG.glVertex2d(x2, y1)
        GG.glVertex2d(x2, y2)
        GG.glVertex2d(x2, y2)
        GG.glVertex2d(x1, y2)
        GG.glVertex2d(x1, y2)
        GG.glVertex2d(x1, y1)
        GG.glEnd()
    }

    @Drawer
    inline fun texture2d(block: () -> Unit) {
        GG.glEnable(GG.GL_TEXTURE_2D)
        GG.glEnable(GG.GL_BLEND)
        block()
        GG.glDisable(GG.GL_BLEND)
        GG.glDisable(GG.GL_TEXTURE_2D)
    }

}

// Vertex 3

data class Vertex3f(val x: Float, val y: Float, val z: Float) {
    constructor(vertex2f: Vertex2f) : this(vertex2f.x, vertex2f.y, 0f)
}

data class Vertex3d(val x: Double, val y: Double, val z: Double)

// Vertex 2

data class Vertex2f(val x: Float, val y: Float) {
    fun addAngle(angle: Float, distance: Float): Vertex2f = Vertex2f(
        x = x + distance * cos(angle),
        y = y + distance * sin(angle)
    )

    infix fun vectorTo(other: Vertex2f): Vector2f = Vector2f(this, other)
    operator fun minus(from: Vertex2f): Vertex2f = Vertex2f(x - from.x, y - from.y)
    operator fun times(m: Float): Vertex2f = Vertex2f(x * m, y * m)
    operator fun plus(other: Vertex2f): Vertex2f = Vertex2f(x + other.x, y + other.y)

    infix fun distanceWith(position: Vertex2f): Float =
        sqrt((position.x - x).pow(2) + (position.y - y).pow(2))
}

data class Vertex2d(val x: Double, val y: Double)
data class Vertex2i(val x: Int, val y: Int)

// Vector 2

data class Vector2f(val from: Vertex2f, val to: Vertex2f) {

    constructor(x1: Float, y1: Float, x2: Float, y2: Float) : this(Vertex2f(x1, y1), Vertex2f(x2, y2))

    val size = from distanceWith to

    val x: Float
        get() = to.x - from.x
    val y: Float
        get() = to.y - from.y

    fun collideWith(other: Vector2f): Boolean {
        val collide1 = collide(other, this)
        val collide2 = collide(this, other)
        return collide1 && collide2
    }

}

// Rectangle

class RectangleD(val top: Double, val right: Double, val bottom: Double, val left: Double) {

    fun center(): Vertex2d {
        val x = left + (right - left) / 2
        val y = top + (bottom - top) / 2
        return Vertex2d(x, y)
    }

    val width: Double get() = right - left

    val height: Double get() = bottom - top

    operator fun contains(point: Vertex2d): Boolean {
        return top <= point.y && right >= point.x && bottom >= point.y && left <= point.x
    }

}
