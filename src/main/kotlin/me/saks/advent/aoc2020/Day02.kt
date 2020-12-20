package me.saks.advent.aoc2020

import me.saks.advent.helpers.*

fun main() {
    "2020/02.txt".readInputLines()
        .map { Regex("""(?<min>\d+)-(?<max>\d+) (?<c>.): (?<pw>.+)""").matchEntire(it)!! }
        .map { Triple(it["min"].toInt(), it["pw"].count { c -> it["c"][0] == c }, it["max"].toInt()) }
        .filter { it.second.between(it.first, it.third) }
        .count()
        .partOneSolution()

    "2020/02.txt".readInputLines()
        .map { Regex("""(?<idx1>\d+)-(?<idx2>\d+) (?<c>.): (?<pw>.+)""").matchEntire(it)!! }
        .map {
            Triple(
                it["idx1"].toInt(),
                it["idx2"].toInt(),
                it["pw"].mapIndexedNotNull { i, c -> i.takeIf { _ -> c == it["c"][0] } })
        }
        .filter { it.third.contains(it.first - 1).xor(it.third.contains(it.second - 1)) }
        .count()
        .partTwoSolution()
}
