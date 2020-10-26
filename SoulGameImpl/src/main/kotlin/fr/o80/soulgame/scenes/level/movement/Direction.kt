package fr.o80.soulgame.scenes.level.movement

enum class Direction(val index: Int) {
    DOWN(0) {
        override val opposite: Direction get() = UP
    },
    LEFT(1) {
        override val opposite: Direction get() = RIGHT
    },
    RIGHT(2) {
        override val opposite: Direction get() = LEFT
    },
    UP(3) {
        override val opposite: Direction get() = DOWN
    };

    val isHorizontal: Boolean get() = this == LEFT || this == RIGHT
    abstract val opposite: Direction

}