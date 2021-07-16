package fr.o80.gamelib.menu

import fr.o80.gamelib.dsl.RectangleD
import fr.o80.gamelib.dsl.Vertex2d
import fr.o80.gamelib.dsl.Vertex3f
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.MouseButtonPipeline
import fr.o80.gamelib.loop.MouseMovePipeline
import fr.o80.gamelib.menu.renderer.ViewRenderer
import fr.o80.gamelib.menu.view.Clickable
import fr.o80.gamelib.menu.view.MenuView
import fr.o80.gamelib.menu.view.ViewState
import fr.o80.gamelib.menu.view.menuview.at
import fr.o80.gamelib.menu.view.menuview.buildRenderers
import org.lwjgl.glfw.GLFW

class Menu private constructor(
    private val views: List<MenuView>,
    private val background: Vertex3f,
    private val renderers: List<ViewRenderer>,
    private var enabled: Boolean
) {

    fun update() {
    }

    fun render() {
        draw {
            clear(background)

            color(1f, 1f, 1f)

            views.forEach { view ->
                renderers
                    .first { it.canRender(view) }
                    .render(view)
            }
        }
    }

    private fun handleClick(x: Double, y: Double) {
        enabled || return
        views.at(x, y)
            ?.let { it as? Clickable }
            ?.onClick()
    }

    private fun handleMove(x: Double, y: Double) {
        views.forEach { view ->
            if (Vertex2d(x, y) in view.bounds) {
                view.onHover()
            } else {
                view.onLeave()
            }
        }
    }

    fun close() {
        renderers.forEach { renderer -> renderer.close() }
    }

    fun enable() {
        enabled = true
    }

    fun disabled() {
        enabled = false
    }

    class MenuBuilder(private val enabled: Boolean = true) {

        private lateinit var mouseButtonPipeline: MouseButtonPipeline
        private lateinit var mouseMovePipeline: MouseMovePipeline

        private lateinit var layout: MenuLayout
        private lateinit var bounds: RectangleD

        private lateinit var background: Vertex3f
        private var textResources: TextResources? = null
        private var titleResources: TextResources? = null

        fun of(top: Double, right: Double, bottom: Double, left: Double): MenuBuilder {
            bounds = RectangleD(top, right, bottom, left)
            return this
        }

        fun withPipelines(mouseButtonPipeline: MouseButtonPipeline, mouseMovePipeline: MouseMovePipeline): MenuBuilder {
            this.mouseButtonPipeline = mouseButtonPipeline
            this.mouseMovePipeline = mouseMovePipeline
            return this
        }

        fun andResources(
            background: Vertex3f,
            textResources: TextResources? = null,
            titleResources: TextResources? = null
        ): MenuBuilder {
            this.background = background
            this.textResources = textResources
            this.titleResources = titleResources
            return this
        }

        fun andLayout(layout: MenuLayout.() -> Unit): MenuBuilder {
            this.layout = MenuLayout(40.0).apply(layout)
            return this
        }

        fun build(): Menu {
            val renderers = buildRenderers(
                buttonResources = textResources,
                textResources = textResources,
                titleResources = titleResources
            )

            layout.computeBounds(bounds, renderers)

            val menu = Menu(
                views = layout.views,
                background = background,
                renderers = renderers,
                enabled = enabled
            )

            mouseButtonPipeline.onButton(GLFW.GLFW_MOUSE_BUTTON_LEFT, GLFW.GLFW_RELEASE) { x, y ->
                menu.handleClick(x, y)
            }
            mouseMovePipeline.onMove { x, y ->
                menu.handleMove(x, y)
            }

            return menu
        }

    }

}

