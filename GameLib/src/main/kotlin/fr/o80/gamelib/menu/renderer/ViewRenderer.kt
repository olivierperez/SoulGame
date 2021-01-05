package fr.o80.gamelib.menu.renderer

import fr.o80.gamelib.menu.view.MenuView

interface ViewRenderer {
    fun canRender(view: MenuView): Boolean
    fun render(view: MenuView)
    fun init()
    fun close()
    fun getHeight(): Double
    fun getWidth(view: MenuView): Double
}
