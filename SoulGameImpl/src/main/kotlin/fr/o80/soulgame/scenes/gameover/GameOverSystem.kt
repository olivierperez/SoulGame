package fr.o80.soulgame.scenes.gameover

import fr.o80.gamelib.service.storage.Storage
import kotlin.math.min

private const val KEY_BEST_SCORE = "BEST_SCORE_"

class GameOverSystem(
    private val storage: Storage,
    private val info: GameOverInfo
) {

    private val key: String = "$KEY_BEST_SCORE${info.levelName}"

    init {
        val best = storage.get(key)?.toLong()

        if (best == null || best < info.score)
            storage.store(key, info.score.toString())
    }

    fun update(state: GameOverState) {
        storage.get(key)
            ?.toLong()
            ?.let { best ->
                state.bestScore = best
            }

        state.score = min(state.score + 1, info.score)
    }
}
