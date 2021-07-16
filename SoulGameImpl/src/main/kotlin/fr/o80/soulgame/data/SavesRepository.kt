package fr.o80.soulgame.data

import fr.o80.soulgame.data.model.Saves

interface SavesRepository {
    fun update(apply: (Saves) -> Unit)
    fun getHighScore(levelCode: Int): Long
    fun isUnlocked(levelCode: Int): Boolean
}
