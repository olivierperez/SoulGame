package fr.o80.gamelib.service.goal

class Goals(
    val mana: Goal?,
    val score: Goal?,
    val ticks: Goal?,
)

sealed interface Goal {
    fun check(result: Any?): Boolean
}

fun Goal?.check(result: Any?): Boolean = this?.check(result) ?: false

class MinGoal(val value: Long) : Goal {
    override fun check(result: Any?): Boolean {
        return (result is Long && value <= result) || (result is Int && value <= result)
    }
}

class MaxGoal(val value: Long) : Goal {
    override fun check(result: Any?): Boolean {
        return (result is Long && result <= value) || (result is Int && result <= value)
    }
}
