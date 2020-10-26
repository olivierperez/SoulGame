package fr.o80.soulgame.scenes.level.movement

enum class Movement(val indices: IntArray) {
    STANDING(intArrayOf(1)),
    MOVING(intArrayOf(0, 2))
}
