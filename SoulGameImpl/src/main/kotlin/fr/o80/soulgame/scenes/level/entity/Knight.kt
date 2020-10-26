package fr.o80.soulgame.scenes.level.entity

import fr.o80.gamelib.drawing.Sprite

class Knight(
    x: Float,
    y: Float,
    size: Float,
    sprite: Sprite,
    speed: Float
) : Entity(x, y, size, sprite, speed) {
    override val characterIndex: Int = 0
}
