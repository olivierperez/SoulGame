package fr.o80.gamelib.service.goal

class GoalParser {

    private val regex = "^(min|max)\\((\\d+)\\)$".toRegex()

    fun parse(value: String?): Goal? {
        if (value == null)
            return null

        return regex.matchEntire(value)?.let { result -> result.groupValues[1].toGoal(result.groupValues[2]) }
               ?: error("Unable to parse the goal: \"$value\"")
    }
}

private fun String.toGoal(value: String): Goal {
    return when (this) {
        "min" -> MinGoal(value.toLong())
        "max" -> MaxGoal(value.toLong())
        else -> error("\"$this\" is not a valid goal operation")
    }
}
