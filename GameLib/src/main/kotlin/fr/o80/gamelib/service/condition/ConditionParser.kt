package fr.o80.gamelib.service.condition

private val parameterRegex = "^\\[(\\w+)]$".toRegex()
private val integerRegex = "-?\\d+$".toRegex()

class ConditionParser {
    private enum class State {
        READING_LEFT,
        READING_RIGHT,
        READING_OPERAND
    }

    fun parse(conditionString: String): Condition {
        var state: State = State.READING_LEFT
        val leftString = StringBuilder()
        val rightString = StringBuilder()
        val operandString = StringBuilder()

        conditionString.forEach { c ->
            if (state == State.READING_LEFT && !c.isOperand()) {
                leftString.append(c)
                return@forEach
            }

            if (state == State.READING_LEFT && c.isOperand()) {
                state = State.READING_OPERAND
            }

            if (state == State.READING_OPERAND && c.isOperand()) {
                operandString.append(c)
                return@forEach
            }

            if (state == State.READING_OPERAND && !c.isOperand()) {
                state = State.READING_RIGHT
            }

            if (state == State.READING_RIGHT) {
                rightString.append(c)
                return@forEach
            }
        }

        return Condition(
            leftString.toOperand(),
            rightString.toOperand(),
            operandString.toOperation()
        )
    }
}

private fun StringBuilder.toOperand(): Operand {
    val value = this.toString()

    if (integerRegex.matches(value)) {
        return IntValueOperand(value.toInt())
    }

    parameterRegex.find(value)?.let {
        return ParamValueOperand(it.groupValues[1])
    }

    error("\"$value\" is not a valid operand!")
}

private fun StringBuilder.toOperation(): Operation {
    return when (this.toString()) {
        "<=" -> Operation.LTE
        ">=" -> Operation.GTE
        "==", "=" -> Operation.EQUAL
        "<>", "!=" -> Operation.DIFFERENT
        else -> error("Operand \"$this\" is not yet handled!")
    }
}

private val operandChars = arrayOf('>', '<', '=', '!')
private fun Char.isOperand(): Boolean {
    return this in operandChars
}
