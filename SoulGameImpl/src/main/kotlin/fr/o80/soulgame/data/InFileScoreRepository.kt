package fr.o80.soulgame.data

import fr.o80.gamelib.service.storage.InFileStorage
import fr.o80.gamelib.service.storage.Storage
import fr.o80.soulgame.data.model.BestScores

private const val FILENAME = "SoulGame.save"

class InFileScoreRepository(
    private val storage: Storage<BestScores> = InFileStorage(BestScores.serializer(), FILENAME)
) : ScoreRepository {

    override fun updateBestScore(levelCode: Int, score: Long) {
        synchronized(storage) {
            val gameData = storage.get() ?: BestScores()
            val bestScore = gameData.levels[levelCode]
            if (bestScore == null || bestScore < score) {
                gameData.levels[levelCode] = score
                storage.store(gameData)
            }
        }
    }

    override fun getBestScore(levelCode: Int): Long {
        return storage.get()?.levels?.get(levelCode) ?: 0
    }
}
