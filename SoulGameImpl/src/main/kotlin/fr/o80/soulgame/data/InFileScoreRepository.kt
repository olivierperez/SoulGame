package fr.o80.soulgame.data

import fr.o80.gamelib.service.storage.InFileStorage
import fr.o80.gamelib.service.storage.Storage
import fr.o80.soulgame.data.model.BestScores

class InFileScoreRepository(
    private val storage: Storage<BestScores> = InFileStorage(BestScores.serializer())
) : ScoreRepository {

    override fun updateBestScore(levelName: String, score: Long) {
        synchronized(storage) {
            val gameData = storage.get() ?: BestScores()
            val bestScore = gameData.levels[levelName]
            if (bestScore == null || bestScore < score) {
                gameData.levels[levelName] = score
                storage.store(gameData)
            }
        }
    }

    override fun getBestScore(levelName: String): Long {
        return storage.get()?.levels?.get(levelName) ?: 0
    }
}
