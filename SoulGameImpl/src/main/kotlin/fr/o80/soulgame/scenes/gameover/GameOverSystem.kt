package fr.o80.soulgame.scenes.gameover

import fr.o80.gamelib.service.storage.InFileStorage
import fr.o80.soulgame.SoulGameData
import kotlin.math.min

class GameOverSystem(
    private val info: GameOverInfo
) {

    private val storage = InFileStorage(SoulGameData.serializer())

    init {
        val gameData = storage.get() ?: SoulGameData()
        val bestScore = gameData.bestScores[info.levelName]

        if (bestScore == null || bestScore < info.score) {
            gameData.bestScores[info.levelName] = info.score
            storage.store(gameData)
        }
    }

    fun update(state: GameOverState) {
        storage.get()
            ?.bestScores?.get(info.levelName)
            ?.let { bestScore ->
                state.bestScore = bestScore
            }

        state.score = min(state.score + 1, info.score)
    }
}
