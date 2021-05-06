package fr.o80.gamelib.service.storage

interface Storage {
    fun store(key: String, value: String)
    fun get(key: String): String?
}
