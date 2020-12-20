package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputSplitBy

fun main() {
    "2020/06.txt".readInputSplitBy("\n\n")
        .sumOf { it.replace("\n", "").toSet().size }
        .partOneSolution()

    "2020/06.txt".readInputSplitBy("\n\n")
        .sumOf { it.split("\n").map { a -> a.toSet() }.reduce { acc, set -> acc.intersect(set) }.size }
        .partTwoSolution()
}
