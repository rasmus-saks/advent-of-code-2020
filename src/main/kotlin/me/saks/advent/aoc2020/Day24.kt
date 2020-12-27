package me.saks.advent.aoc2020

import me.saks.advent.helpers.*
import java.util.*

fun main() {
    var blackTiles = setOf<HexCell>()
    "2020/24.txt"
        .readInputLines()
        .forEach {
            var tile = HexCell(0, 0, 0)
            val sc = Scanner(it).useDelimiter("")
            while (sc.hasNext()) {
                tile = when (sc.next()) {
                    "e" -> tile.east()
                    "w" -> tile.west()
                    "s" -> when (sc.next()) {
                        "e" -> tile.southEast()
                        "w" -> tile.southWest()
                        else -> TODO()
                    }
                    "n" -> when (sc.next()) {
                        "e" -> tile.northEast()
                        "w" -> tile.northWest()
                        else -> TODO()
                    }
                    else -> TODO()
                }
            }
            if (tile in blackTiles) {
                blackTiles -= tile
            } else {
                blackTiles += tile
            }
        }
    blackTiles.size.partOneSolution()

    repeat(100) {
        blackTiles = step(blackTiles)
    }
    blackTiles.size.partTwoSolution()
}

private fun step(blackTiles: Set<HexCell>): Set<HexCell> {
    val wholeGrid = blackTiles.flatMap { it.neighbours() + it }.toSet()
    val newBlackTiles = blackTiles.toMutableSet()
    wholeGrid.forEach { tile ->
        if (tile !in blackTiles && tile.neighbours().count { it in blackTiles } == 2) {
            newBlackTiles.add(tile)
        }
        if (tile in blackTiles && tile.neighbours().count { it in blackTiles }.let { it == 0 || it > 2 }) {
            newBlackTiles.remove(tile)
        }
    }
    return newBlackTiles
}
