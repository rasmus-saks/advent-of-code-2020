package me.saks.advent.aoc2020

import me.saks.advent.helpers.get
import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputSplitBy

val ALL_FIELDS = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"/*, "cid"*/)
val KEY_VALUE = Regex("""(?<key>.+):(?<value>.+)""")
val VALIDATIONS = mapOf<String, (String) -> Boolean>(
    "byr" to { it.toLong() in 1920..2002 },
    "iyr" to { it.toLong() in 2010..2020 },
    "eyr" to { it.toLong() in 2020..2030 },
    "hgt" to {
        it.endsWith("cm") && it.dropLast(2).toLong() in 150..193
                || it.endsWith("in") && it.dropLast(2).toLong() in 59..76
    },
    "hcl" to { it.matches("#[0-9a-f]{6}".toRegex()) },
    "ecl" to { it in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
    "pid" to { it.matches("\\d{9}".toRegex()) },
    "cid" to { true },
)

fun main() {
    "2020/04.txt".readInputSplitBy("\n\n")
        .count { line ->
            (ALL_FIELDS - line.split("\n", " ").map { KEY_VALUE.matchEntire(it)!!["key"] }).isEmpty()
        }
        .partOneSolution()

    "2020/04.txt".readInputSplitBy("\n\n")
        .count { line ->
            (ALL_FIELDS - line.split("\n", " ")
                .map { KEY_VALUE.matchEntire(it)!! }
                .filter { VALIDATIONS[it["key"]]?.invoke(it["value"]) == true }
                .map { it["key"] }).isEmpty()
        }
        .partTwoSolution()
}
