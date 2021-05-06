package fr.o80.gamelib.service.storage

import java.util.concurrent.atomic.AtomicReference

class InMemoryStorage<T> : Storage<T> {

    private val storage = AtomicReference<T?>(null)

    override fun store(value: T) {
        storage.set(value)
    }

    override fun get(): T? {
        return storage.get()
    }
}
