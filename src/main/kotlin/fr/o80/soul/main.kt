package fr.o80.soul

fun main() {
    println("main ${Thread.currentThread().id} - ${Thread.currentThread().name}")
    val game = Game()
    game.start()
}