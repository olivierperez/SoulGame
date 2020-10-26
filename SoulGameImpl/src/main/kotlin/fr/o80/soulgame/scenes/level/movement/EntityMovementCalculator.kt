package fr.o80.soulgame.scenes.level.movement

import fr.o80.soulgame.scenes.level.entity.Entity

interface EntityMovementCalculator<E : Entity> {
    fun update(entity: E)
}