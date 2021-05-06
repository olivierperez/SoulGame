package fr.o80.soulgame.data

interface ScoreRepository {
    fun updateBestScore(levelName: String, score: Long)
    fun getBestScore(levelName: String): Long
}
