package fr.o80.soulgame.scenes.level.entity

import fr.o80.gamelib.drawing.Sprite
import fr.o80.soulgame.scenes.level.level.Point

class Soul(
    x: Float,
    y: Float,
    size: Float,
    sprite: Sprite,
    speed: Float
) : Entity(x, y, size, sprite, speed) {

    var team: Team = Team.UNDECIDED

    override val characterIndex: Int get() = team.characterIndex

    var lastDecisionTile: Point = Point(-1000, -1000)
}

enum class Team(val characterIndex: Int) {
    UPPER(1),
    LOWER(2),
    UNDECIDED(3)
}
