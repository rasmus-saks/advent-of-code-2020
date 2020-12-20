package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputLines

fun main() {
    val adapters = "2020/10.txt"
        .readInputLines()
        .map { it.toLong() }
        .sorted()

    adapters
        .foldIndexed(Pair(0, 1)) { idx, acc, a ->
            when (adapters.getOrNull(idx - 1)?.let { a - it } ?: adapters[0]) {
                1L -> Pair(acc.first + 1, acc.second)
                2L -> acc
                3L -> Pair(acc.first, acc.second + 1)
                else -> throw IllegalStateException()
            }
        }
        .run { first * second }
        .partOneSolution()

    val answer = mutableMapOf(0L to 1L)
    adapters.forEach {
        answer[it] = (answer[it - 1] ?: 0) + (answer[it - 2] ?: 0) + (answer[it - 3] ?: 0)
    }
    answer[adapters.maxOrNull()!!].partTwoSolution()
}
