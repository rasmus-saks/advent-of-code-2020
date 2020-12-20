package me.saks.advent.aoc2020

import me.saks.advent.helpers.*

fun main() {
    val input = "2020/13.txt"
        .readInputLines()
    val earliest = input[0].toLong()
    input[1]
        .split(",")
        .mapNotNull { it.toLongOrNull() }
        .minByOrNull { it - earliest % it }!!
        .let { (it * (earliest / it + 1) - earliest) * it }
        .partOneSolution()

    input[1]
        .split(",")
        .mapIndexed { idx, bus -> Pair(idx, bus) }
        .filter { (_, bus) -> bus != "x" }
        .fold(Pair(listOf<Long>(), listOf<Long>())) { acc, (idx, bus) ->
            acc + Pair(bus.toLong(), Math.floorMod(-idx, bus.toInt()).toLong())
        }
        .let { (buses, remainders) -> chineseRemainder(buses, remainders) }
        .partTwoSolution()
}
