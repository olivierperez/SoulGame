package fr.o80.soulgame

fun main() {
    println("main ${Thread.currentThread().id} - ${Thread.currentThread().name}")
    val game = SoulGame()
    game.start()
}