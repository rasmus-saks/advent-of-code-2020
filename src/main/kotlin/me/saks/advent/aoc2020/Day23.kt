package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputLines

fun main() {
    val input = "2020/23.txt".readInputLines().single().map { it.toString().toInt() }
    val inputCups = IntArray(10)
    repeat(9) {
        inputCups[input[it]] = input[(input.indexOf(input[it]) + 1) % input.size]
    }
    val cups1 = inputCups.copyInto(IntArray(10))
    play(cups1, input[0], 100)
    var solution = ""
    var current = cups1[1]
    repeat(cups1.size - 2) {
        solution += current
        current = cups1[current]
    }
    solution.partOneSolution()

    val cups2 = inputCups.copyInto(IntArray(1_000_001))
    cups2[input.last()] = 10
    cups2[1_000_000] = input.first()
    (10 until 1_000_000).forEach {
        cups2[it] = it + 1
    }
    play(cups2, input[0], 10_000_000)
    (cups2[1].toLong() * cups2[cups2[1]].toLong()).partTwoSolution()
}

fun play(cups: IntArray, first: Int, moves: Int = 100) {
    val maxCup = cups.maxOrNull()!!
    var current = first
    repeat(moves) {
        val picked1 = cups[current]
        val picked2 = cups[picked1]
        val picked3 = cups[picked2]
        var dest = current
        do {
            dest--
            if (dest < 1) dest = maxCup
        } while (dest == picked1 || dest == picked2 || dest == picked3)
        cups[current] = cups[picked3]
        cups[picked3] = cups[dest]
        cups[dest] = picked1
        current = cups[current]
    }
}
