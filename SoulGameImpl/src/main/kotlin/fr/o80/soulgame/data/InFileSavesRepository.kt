package fr.o80.soulgame.data

import fr.o80.gamelib.service.storage.InFileStorage
import fr.o80.gamelib.service.storage.Storage
import fr.o80.soulgame.data.model.Saves

private const val FILENAME = "SoulGame.save"

class InFileSavesRepository(
    private val storage: Storage<Saves> = InFileStorage(Saves.serializer(), FILENAME)
) : SavesRepository {

    override fun update(apply: (Saves) -> Unit) {
        synchronized(storage) {
            val saves = storage.get() ?: Saves()
            apply(saves)
            storage.store(saves)
        }
    }

    override fun getHighScore(levelCode: Int): Long {
        return storage.get()?.levels?.get(levelCode)?.highScore ?: 0L
    }

    override fun isUnlocked(levelCode: Int): Boolean {
        return storage.get()?.levels?.get(levelCode)?.unlocked ?: false
    }
}
