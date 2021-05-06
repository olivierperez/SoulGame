package fr.o80.gamelib.service.storage

class InMemoryStorage : Storage {

    private val storage: MutableMap<String, String> = mutableMapOf()

    override fun store(key: String, value: String) {
        storage[key] = value
    }

    override fun get(key: String): String? {
        return storage[key]
    }
}
