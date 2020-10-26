package fr.o80.soulgame.scenes.level.drawing.selector

import fr.o80.soulgame.scenes.level.level.Block

interface TileSelector {
    fun get(block: Block, x: Int, y: Int): Pair<Int, Int>
}
