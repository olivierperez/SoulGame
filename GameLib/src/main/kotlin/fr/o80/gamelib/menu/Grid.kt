package fr.o80.gamelib.menu

import fr.o80.gamelib.dsl.RectangleD
import fr.o80.gamelib.dsl.Vertex2d
import fr.o80.gamelib.dsl.Vertex3f
import fr.o80.gamelib.dsl.draw
import fr.o80.gamelib.loop.MouseButtonPipeline
import fr.o80.gamelib.loop.MouseMovePipeline
import fr.o80.gamelib.menu.renderer.ButtonViewRenderer
import fr.o80.gamelib.menu.view.Clickable
import fr.o80.gamelib.menu.view.MenuView
import fr.o80.gamelib.menu.view.ViewState
import fr.o80.gamelib.menu.view.menuview.at
import org.lwjgl.glfw.GLFW

class Grid private constructor(
    private val views: List<MenuView>,
    private val background: Vertex3f,
    private val renderer: ButtonViewRenderer
) {

    fun update() {
    }

    fun render() {
        draw {
            clear(background)

            color(1f, 1f, 1f)

            views.forEach { view ->
                renderer.render(view)
            }
        }
    }

    fun close() {
         renderer.close()
    }

    private fun handleClick(x: Double, y: Double) {
        views.at(x, y)
            ?.let { it as? Clickable }
            ?.onClick()
    }

    private fun handleMove(x: Double, y: Double) {
        views.forEach { view ->
            if (Vertex2d(x, y) in view.bounds) {
                view.state = ViewState.HOVER
            } else {
                view.state = ViewState.NORMAL
            }
        }
    }

    class GridBuilder {

        private lateinit var mouseButtonPipeline: MouseButtonPipeline
        private lateinit var mouseMovePipeline: MouseMovePipeline

        private lateinit var layout: GridLayout
        private lateinit var bounds: RectangleD
        private var cols: Int = -1
        private var horizontalSpacing: Double = -1.0
        private var verticalSpacing: Double = -1.0

        private lateinit var background: Vertex3f
        private var textResources: TextResources? = null
        private var titleResources: TextResources? = null

        fun of(top: Double, left: Double, right: Double, bottom: Double): GridBuilder {
            this.bounds = RectangleD(top, right, bottom, left)
            return this
        }

        fun withDimens(cols: Int, horizontalSpacing: Double, verticalSpacing: Double): GridBuilder {
            this.cols = cols
            this.horizontalSpacing = horizontalSpacing
            this.verticalSpacing = verticalSpacing
            return this
        }

        fun withPipelines(mouseButtonPipeline: MouseButtonPipeline, mouseMovePipeline: MouseMovePipeline): GridBuilder {
            this.mouseButtonPipeline = mouseButtonPipeline
            this.mouseMovePipeline = mouseMovePipeline
            return this
        }

        fun withResources(
            background: Vertex3f,
            textResources: TextResources,
            titleResources: TextResources
        ): GridBuilder {
            this.background = background
            this.textResources = textResources
            this.titleResources = titleResources
            return this
        }

        fun andLayout(layout: GridLayout.() -> Unit): GridBuilder {
            this.layout = GridLayout().apply(layout)
            return this
        }

        fun build(): Grid {
            val buttonViewRenderer = textResources?.let { resources ->
                ButtonViewRenderer(resources).also { it.init() }
            } ?: error("Text resources must be set when using a Grid.")

            layout.computeBounds(
                bounds = bounds,
                cols = cols,
                horizontalSpacing = horizontalSpacing,
                verticalSpacing = verticalSpacing,
                buttonViewRenderer
            )

            val grid = Grid(
                views = layout.views,
                background = background,
                renderer = buttonViewRenderer
            )

            mouseButtonPipeline.onButton(GLFW.GLFW_MOUSE_BUTTON_LEFT, GLFW.GLFW_RELEASE) { x, y ->
                grid.handleClick(x, y)
            }
            mouseMovePipeline.onMove { x, y ->
                grid.handleMove(x, y)
            }

            return grid
        }

    }
}
