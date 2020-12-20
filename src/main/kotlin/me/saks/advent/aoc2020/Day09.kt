package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputLines
import org.apache.commons.collections4.queue.CircularFifoQueue

fun main() {
    val input = "2020/09.txt"
        .readInputLines()
        .map { it.toLong() }
    val buffer = CircularFifoQueue<Long>(25)
    val solution = input.dropWhile { !buffer.isAtFullCapacity && buffer.add(it) }
        .first { !(isSumOfAnyTwo(it, buffer) && buffer.add(it)) }
        .partOneSolution()

    input.indices.forEach { i ->
        (i + 1..input.size).forEach { j ->
            input.subList(i, j).let {
                if (it.sum() == solution) {
                    (it.min()!! + it.max()!!).partTwoSolution()
                    return
                }
            }
        }
    }

}

fun isSumOfAnyTwo(sum: Long, vals: CircularFifoQueue<Long>): Boolean {
    return (0 until vals.size).any { i -> (i + 1 until vals.size).any { j -> sum == vals[i] + vals[j] } }
}
