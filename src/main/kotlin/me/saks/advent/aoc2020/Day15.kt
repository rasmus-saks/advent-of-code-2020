package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputSplitBy

private val LAST_SPOKEN: MutableMap<Int, Int> = hashMapOf()

fun main() {
    solution(2020).partOneSolution()
    solution(30000000).partTwoSolution()
}

fun solution(finalTurn: Int): Int {
    LAST_SPOKEN.clear()
    var last = 0
    "2020/15.txt"
        .readInputSplitBy(",")
        .forEachIndexed { idx, n ->
            LAST_SPOKEN[n.toInt()] = idx + 1
            last = n.toInt()
        }
    (LAST_SPOKEN.keys.size + 1..finalTurn).forEach { turn ->
        if (LAST_SPOKEN[last] == null) {
            LAST_SPOKEN[last] = turn - 1
            last = 0
        } else {
            val num = turn - 1 - LAST_SPOKEN[last]!!
            LAST_SPOKEN[last] = turn - 1
            last = num
        }
    }
    return last
}
