package fr.o80.gamelib.service.storage

interface Storage<T> {
    fun store(value: T)
    fun get(): T?
}
