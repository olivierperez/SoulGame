package fr.o80.soulgame.scenes.level.level

import fr.o80.soulgame.scenes.level.entity.Entity
import fr.o80.soulgame.scenes.level.entity.Soul
import fr.o80.soulgame.scenes.level.entity.Team

abstract class Block(val x: Int, val y: Int) {
    abstract fun canAccept(entity: Entity): Boolean
    abstract fun canBeTriggeredBy(entity: Entity): Boolean
}

class Wall(x: Int, y: Int) : Block(x, y) {
    override fun canAccept(entity: Entity): Boolean = false
    override fun canBeTriggeredBy(entity: Entity): Boolean = true
}

class Door(x: Int, y: Int) : Block(x, y) {
    override fun canAccept(entity: Entity): Boolean =
        entity is Soul && entity.team == Team.UPPER

    override fun canBeTriggeredBy(entity: Entity): Boolean =
        entity is Soul && entity.team == Team.UPPER
}
