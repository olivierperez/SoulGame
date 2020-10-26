package fr.o80.soulgame.scenes.level

import java.util.concurrent.atomic.AtomicLong

class Score {

    private val _value: AtomicLong = AtomicLong(0)

    val value: Long get() = _value.get()

    fun increase() {
        _value.updateAndGet { it + 1 }
    }

    fun reset() {
        _value.set(0)
    }

}
