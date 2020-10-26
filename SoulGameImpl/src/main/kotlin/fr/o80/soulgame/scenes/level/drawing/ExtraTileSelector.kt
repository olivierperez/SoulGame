package fr.o80.soulgame.scenes.level.drawing

import fr.o80.soulgame.scenes.level.drawing.selector.TileSelector
import fr.o80.soulgame.scenes.level.level.Block
import fr.o80.soulgame.scenes.level.level.Door

class ExtraTileSelector : TileSelector {
    override fun get(block: Block, x: Int, y: Int): Pair<Int, Int> {
        return when (block) {
            is Door -> Pair(0, 0)
            else -> TODO("Block ${block::class.simpleName} not yet handled")
        }
    }
}