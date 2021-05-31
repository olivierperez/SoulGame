package fr.o80.soulgame.scenes.level

import java.util.concurrent.atomic.AtomicInteger

class Mana(
    initial: Int,
    val max: Int,
    private val loss: Int
) {

    private val _remaining = AtomicInteger(initial)
    val remaining: Int get() = _remaining.get()

    fun update(): Int {
        return _remaining.updateAndGet { it - loss }
    }

    fun increase(ticks: Int) {
        _remaining.getAndUpdate { value -> (value + ticks).coerceAtMost(max) }
    }

}
