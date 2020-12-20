package me.saks.advent.aoc2020

import me.saks.advent.helpers.*

fun main() {
    "2020/01.txt".readInputLines()
        .toInt()
        .allPairs()
        .find { (a, b) -> a + b == 2020 }
        ?.let { (a, b) -> a * b }
        .partOneSolution()

    "2020/01.txt".readInputLines()
        .toInt()
        .allTriples()
        .find { (a, b, c) -> a + b + c == 2020 }
        ?.let { (a, b, c) -> a * b * c }
        .partTwoSolution()
}
