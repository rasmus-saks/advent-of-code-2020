package me.saks.advent

fun main() {
    val seats = "day05.txt".readInputLines()
        .map { it.replace("[FL]".toRegex(), "0").replace("[BR]".toRegex(), "1") }
        .map { it.toInt(2) }

    seats.maxOrNull().partOneSolution()

    (seats.minOrNull()!!..seats.maxOrNull()!! subtract seats)
        .single()
        .partTwoSolution()
}
