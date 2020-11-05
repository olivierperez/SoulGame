package fr.o80.soulgame.scenes.level

import java.util.concurrent.atomic.AtomicInteger

class Timing(val initialTicks: Int) {

    private val _remainingTicks = AtomicInteger(initialTicks)
    val remainingTicks: Int get() = _remainingTicks.get()

    fun update(): Int {
        return _remainingTicks.decrementAndGet()
    }

    fun increase(ticks: Int) {
        _remainingTicks.getAndUpdate { value -> (value + ticks).coerceAtMost(initialTicks) }
    }

}
