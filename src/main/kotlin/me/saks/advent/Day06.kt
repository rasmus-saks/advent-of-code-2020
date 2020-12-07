package me.saks.advent

fun main() {
    "day06.txt".readInputSplitBy("\n\n")
        .sumOf { it.replace("\n", "").toSet().size }
        .partOneSolution()

    "day06.txt".readInputSplitBy("\n\n")
        .sumOf { it.split("\n").map { a -> a.toSet() }.reduce { acc, set -> acc.intersect(set) }.size }
        .partTwoSolution()
}
