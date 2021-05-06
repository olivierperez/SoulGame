package fr.o80.soulgame.scenes.gameover

import fr.o80.soulgame.data.ScoreRepository
import kotlin.math.min

class GameOverSystem(
    private val info: GameOverInfo,
    private val repository: ScoreRepository
) {

    init {
        val bestScore = repository.getBestScore(info.levelName)
        if (bestScore < info.score) {
            repository.updateBestScore(info.levelName, info.score)
        }
    }

    fun update(state: GameOverState) {
        state.bestScore = repository.getBestScore(info.levelName)
        state.score = min(state.score + 1, info.score)
    }
}
