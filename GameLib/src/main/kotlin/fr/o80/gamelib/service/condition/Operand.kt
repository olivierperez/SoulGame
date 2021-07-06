package fr.o80.gamelib.service.condition

sealed interface Operand {
    fun toValue(params: Map<String, Long>): Long
}

data class LongValueOperand(private val value: Long): Operand {
    override fun toValue(params: Map<String, Long>): Long {
        return value
    }
}

data class ParamValueOperand(val parameter: String): Operand {
    override fun toValue(params: Map<String, Long>): Long {
        return params[parameter] ?: throw IllegalArgumentException("Parameter \"$parameter\" is not found in $parameter")
    }
}
