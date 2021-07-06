package fr.o80.gamelib.service.condition

class ConditionResolver {

    fun resolve(condition: Condition, params: Map<String, Long>) : Boolean {
        val rightValue = condition.right.toValue(params)
        val leftValue = condition.left.toValue(params)

        return when(condition.operation) {
            Operation.LTE -> leftValue <= rightValue
            Operation.EQUAL -> leftValue == rightValue
            Operation.DIFFERENT -> leftValue != rightValue
            Operation.GTE -> leftValue >= rightValue
        }
    }
}
