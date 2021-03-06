package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputLines

fun main() {
    val input = "2020/03.txt".readInputLines()

    slope(3, 1)
        .takeWhile { it.second < input.size }
        .count { input[it.second][it.first % input[it.second].length] == '#' }
        .partOneSolution()

    listOf(slope(1, 1), slope(3, 1), slope(5, 1), slope(7, 1), slope(1, 2))
        .map { slope ->
            slope
                .takeWhile { it.second < input.size }
                .count { input[it.second][it.first % input[it.second].length] == '#' }
                .toLong() // Multiplication result overflows an Int
        }
        .reduce { acc, i -> acc * i }
        .partTwoSolution()

}

fun slope(right: Int, down: Int): Sequence<Pair<Int, Int>> {
    return generateSequence(Pair(right, down)) {
        Pair(it.first + right, it.second + down)
    }
}
