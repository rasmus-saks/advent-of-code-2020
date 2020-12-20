package me.saks.advent.aoc2020

import me.saks.advent.helpers.*

fun main() {
    var activeCubes3 = mutableListOf<Vector<Vector3>>()
    var activeCubes4 = mutableListOf<Vector<Vector4>>()
    "2020/17.txt"
        .readInputLines()
        .forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '#') {
                    activeCubes3.add(Vector3(x, y, 0))
                    activeCubes4.add(Vector4(x, y, 0, 0))
                }
            }
        }
    repeat(6) {
        activeCubes3 = cycle(activeCubes3).toMutableList()
    }
    activeCubes3.size.partOneSolution()
    repeat(6) {
        activeCubes4 = cycle(activeCubes4).toMutableList()
    }
    activeCubes4.size.partTwoSolution()
}

fun <T> cycle(activeCubes: List<Vector<T>>): List<Vector<T>> {
    val (min, max) = activeCubes.bounds()
    return (min - 1..max + 1).mapNotNull {
        val activeNeighbors = it.neighbors().count { n -> activeCubes.contains(n) }
        val isActive = activeCubes.contains(it)
        if (isActive && activeNeighbors in 2..3 || activeNeighbors == 3) {
            it
        } else {
            null
        }
    }.toList()
}
