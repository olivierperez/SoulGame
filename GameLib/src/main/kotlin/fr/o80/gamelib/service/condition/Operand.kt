package fr.o80.gamelib.service.condition

sealed interface Operand {
    fun toValue(params: Map<String, Int>): Int
}

data class IntValueOperand(private val value: Int): Operand {
    override fun toValue(params: Map<String, Int>): Int {
        return value
    }
}

data class ParamValueOperand(val parameter: String): Operand {
    override fun toValue(params: Map<String, Int>): Int {
        return params[parameter] ?: throw IllegalArgumentException("Parameter \"$parameter\" is not found in $parameter")
    }
}
