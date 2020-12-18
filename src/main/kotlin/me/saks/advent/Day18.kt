package me.saks.advent


private val OPERATIONS = mapOf<Char, (Long, Long) -> Long>(
    '+' to { a, b -> a + b },
    '*' to { a, b -> a * b },
)

fun main() {
    "day18.txt"
        .readInputLines()
        .map { parse(it, listOf("+*")).evaluate() }
        .sum()
        .partOneSolution()

    "day18.txt"
        .readInputLines()
        .map { parse(it, listOf("+", "*")).evaluate() }
        .sum()
        .partTwoSolution()
}

/**
 * @param orderOfOperations Order of operations from highest to lowest precedence, each String contains single character operators of the same precedence
 */
fun parse(exprStr: String, orderOfOperations: List<String>): Expression {
    return exprStr.toLongOrNull()?.let { Expression.Constant(it) }
        ?: orderOfOperations
            .reversed()
            .mapNotNull { ops -> parseOperations(exprStr, ops) { parse(it, orderOfOperations) } }
            .firstOrNull()
        ?: parse(exprStr.substring(1 until exprStr.length - 1), orderOfOperations)
}

private fun parseOperations(exprStr: String, operations: String, parse: (String) -> Expression): Expression? {
    var level = 0
    var i = exprStr.length - 1
    while (i >= 0) {
        when (val c = exprStr[i]) {
            ')' -> level++
            '(' -> level--
            in operations -> if (level == 0) {
                val left = exprStr.substring(0, i - 1)
                val right = exprStr.substring(i + 2, exprStr.length)
                return Expression.Operation(parse(left), c, parse(right))
            }
        }
        i--
    }
    return null
}

sealed class Expression {
    abstract fun evaluate(): Long
    data class Operation(
        val left: Expression,
        val operation: Char,
        val right: Expression
    ) : Expression() {
        override fun evaluate(): Long {
            return OPERATIONS[operation]!!(left.evaluate(), right.evaluate())
        }

        override fun toString(): String {
            return "($left $operation $right)"
        }
    }

    data class Constant(
        val value: Long
    ) : Expression() {
        override fun evaluate(): Long {
            return value
        }

        override fun toString(): String {
            return value.toString()
        }
    }
}
