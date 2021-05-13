package fr.o80.gamelib.dsl

import fr.o80.gamelib.GG

@Drawer
fun Draw.alpha(block: Draw.() -> Unit) {
    GG.glEnable(GG.GL_BLEND)
    GG.glBlendFunc(GG.GL_SRC_ALPHA, GG.GL_ONE_MINUS_SRC_ALPHA)
    block()
    GG.glDisable(GG.GL_BLEND)
}