package fr.o80.soulgame.data

interface ScoreRepository {
    fun updateBestScore(levelCode: Int, score: Long)
    fun getBestScore(levelCode: Int): Long
}
