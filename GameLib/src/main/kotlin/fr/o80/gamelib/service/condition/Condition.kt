package fr.o80.gamelib.service.condition

data class Condition(
    val left: Operand,
    val right: Operand,
    val operation: Operation
)
