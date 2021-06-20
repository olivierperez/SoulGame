package fr.o80.gamelib.menu.view.menuview

import fr.o80.gamelib.dsl.Vertex2d
import fr.o80.gamelib.menu.TextResources
import fr.o80.gamelib.menu.renderer.ButtonViewRenderer
import fr.o80.gamelib.menu.renderer.TextViewRenderer
import fr.o80.gamelib.menu.renderer.TitleViewRenderer
import fr.o80.gamelib.menu.renderer.ViewRenderer
import fr.o80.gamelib.menu.view.MenuView

fun List<MenuView>.at(x: Double, y: Double): MenuView? {
    return this.firstOrNull { Vertex2d(x, y) in it.bounds }
}

fun buildRenderers(
    buttonResources: TextResources?,
    textResources: TextResources?,
    titleResources: TextResources?,
): List<ViewRenderer> {
    val renderers = mutableListOf<ViewRenderer>()

    buttonResources?.let { resources ->
        ButtonViewRenderer(resources).also {
            it.init()
            renderers.add(it)
        }
    }

    textResources?.let { resources ->
        TextViewRenderer(resources).also {
            it.init()
            renderers.add(it)
        }
    }

    titleResources?.let { resources ->
        TitleViewRenderer(resources).also {
            it.init()
            renderers.add(it)
        }
    }

    return renderers
}
