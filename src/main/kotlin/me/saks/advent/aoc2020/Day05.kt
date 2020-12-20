package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputLines

fun main() {
    val seats = "2020/05.txt".readInputLines()
        .map { it.replace("[FL]".toRegex(), "0").replace("[BR]".toRegex(), "1") }
        .map { it.toInt(2) }

    seats.maxOrNull().partOneSolution()

    (seats.minOrNull()!!..seats.maxOrNull()!! subtract seats)
        .single()
        .partTwoSolution()
}
