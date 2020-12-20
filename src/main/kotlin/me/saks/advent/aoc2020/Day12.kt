package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputLines
import kotlin.math.absoluteValue

val INSTRUCTIONS: Map<Char, (Ship, Long) -> Ship> = mapOf(
    'N' to { s, arg -> s.copy(north = s.north + arg) },
    'S' to { s, arg -> s.copy(north = s.north - arg) },
    'E' to { s, arg -> s.copy(east = s.east + arg) },
    'W' to { s, arg -> s.copy(east = s.east - arg) },
    'L' to { s, arg -> s.copy(dir = s.dir.rotate(arg)) },
    'R' to { s, arg -> s.copy(dir = s.dir.rotate(-arg)) },
    'F' to { s, arg ->
        s.copy(
            east = s.east + s.dir.east * arg,
            north = s.north + s.dir.north * arg
        )
    },
)

val INSTRUCTIONS2: Map<Char, (Ship, Long) -> Ship> = mapOf(
    'N' to { s, arg -> s.copy(dir = s.dir.copy(north = s.dir.north + arg)) },
    'S' to { s, arg -> s.copy(dir = s.dir.copy(north = s.dir.north - arg)) },
    'E' to { s, arg -> s.copy(dir = s.dir.copy(east = s.dir.east + arg)) },
    'W' to { s, arg -> s.copy(dir = s.dir.copy(east = s.dir.east - arg)) },
    'L' to { s, arg -> s.copy(dir = s.dir.rotate(arg)) },
    'R' to { s, arg -> s.copy(dir = s.dir.rotate(-arg)) },
    'F' to { s, arg ->
        s.copy(
            east = s.east + s.dir.east * arg,
            north = s.north + s.dir.north * arg
        )
    },
)

fun main() {
    "2020/12.txt"
        .readInputLines()
        .fold(Ship()) { ship, instr ->
            INSTRUCTIONS[instr[0]]!!(ship, instr.substring(1).toLong())
        }
        .let { it.east.absoluteValue + it.north.absoluteValue }
        .partOneSolution()

    "2020/12.txt"
        .readInputLines()
        .fold(Ship(Direction(10, 1))) { ship, instr ->
            INSTRUCTIONS2[instr[0]]!!(ship, instr.substring(1).toLong())
        }
        .let { it.east.absoluteValue + it.north.absoluteValue }
        .partTwoSolution()
}

data class Direction(val east: Long, val north: Long) {
    fun rotate(deg: Long): Direction {
        return when (if (deg < 0) deg + 360 else deg) {
            90L -> Direction(-north, east)
            180L -> Direction(-east, -north)
            270L -> Direction(north, -east)
            else -> throw IllegalArgumentException()
        }
    }
}

data class Ship(val dir: Direction = Direction(1, 0), val east: Long = 0, val north: Long = 0)
