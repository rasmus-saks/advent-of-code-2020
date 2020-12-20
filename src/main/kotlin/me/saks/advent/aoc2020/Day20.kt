package me.saks.advent.aoc2020

import me.saks.advent.helpers.*
import kotlin.math.sqrt

private typealias TileTransforms = Map<TileId, Tiles>
private typealias Tiles = List<Tile>
private typealias Tile = List<String>
private typealias TileId = Int
private typealias GridTile = Pair<TileId, Tile>
private typealias Grid = List<GridTile>

private val GRID_HEIGHT = sqrt("2020/20.txt".readInputSplitBy("\n\n").size.toDouble()).toInt()
private val GRID_WIDTH = GRID_HEIGHT
private const val TILE_HEIGHT = 10
private const val TILE_WIDTH = 10

private val SEA_MONSTER_OFFSETS: List<Pair<Int, Int>> = """                  # 
#    ##    ##    ###
 #  #  #  #  #  #   """
    .split("\n")
    .flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
            if (c == '#') {
                Pair(x, y)
            } else null
        }
    }

fun main() {
    val allTiles: TileTransforms = "2020/20.txt"
        .readInputSplitBy("\n\n")
        .map { it.split("\n") }
        .fold(mutableMapOf()) { tiles, it ->
            val id = it[0].substring(5, 9).toInt()
            val tile = it.rest()
            tiles[id] = tile.allTransforms()
            tiles
        }
    val arranged = arrangeTiles(allTiles)
    listOf(
        arranged[0, 0],
        arranged[0, GRID_HEIGHT - 1],
        arranged[GRID_WIDTH - 1, 0],
        arranged[GRID_WIDTH - 1, GRID_HEIGHT - 1],
    ).fold(1L) { acc, tile -> acc * tile.first }
        .partOneSolution()

    combineGrid(arranged)
        .allTransforms()
        .map { Pair(findSeaMonsters(it), it) }
        .single { (monsters, _) -> monsters.isNotEmpty() }
        .let { (monsters, tile) ->
            tile.joinToString("").count { it == '#' } - monsters.size * (SEA_MONSTER_OFFSETS.size)
        }
        .partTwoSolution()
}

private fun Tile.topBorder(): String = this[0]
private fun Tile.leftBorder(): String = this.column(0)
private fun Tile.rightBorder(): String = this.column(TILE_WIDTH - 1)
private fun Tile.bottomBorder(): String = this[TILE_HEIGHT - 1]
private fun Tile.pretty(): String = this.joinToString("\n")
private fun Grid.nextCoordinates(): Pair<Int, Int> = size.let { Pair(it % GRID_WIDTH, it / GRID_WIDTH) }
private operator fun Grid.get(x: Int, y: Int): GridTile = this[y * GRID_WIDTH + x]
private operator fun Tile.get(x: Int, y: Int): Char? = this.getOrNull(y)?.getOrNull(x)

private fun arrangeTiles(
    remainingTiles: TileTransforms,
    grid: Grid = listOf()
): Grid {
    val (_, y) = grid.nextCoordinates()
    if (y == GRID_HEIGHT) return grid
    remainingTiles.forEach { (id, tiles) ->
        val withoutThisOne = remainingTiles.filterKeys { it != id }
        tiles.forEach { tile ->
            if (nextTileFits(tile, grid, withoutThisOne)) {
                return arrangeTiles(withoutThisOne, grid + Pair(id, tile))
            }
        }
    }
    return listOf()
}

private fun nextTileFits(nextTile: Tile, grid: Grid, remaining: TileTransforms): Boolean {
    val (x, y) = grid.nextCoordinates()
    if (x == 0 && y == 0) {
        return remaining.all { (_, tiles) -> tiles.all { it.rightBorder() != nextTile.leftBorder() && it.bottomBorder() != nextTile.topBorder() } }
    }
    if (x == 0) {
        return grid[x, y - 1].second.bottomBorder() == nextTile.topBorder()
    }
    if (y == 0) {
        return grid[x - 1, y].second.rightBorder() == nextTile.leftBorder()
    }
    return grid[x - 1, y].second.rightBorder() == nextTile.leftBorder()
            && grid[x, y - 1].second.bottomBorder() == nextTile.topBorder()
}

private fun Tile.allTransforms(): List<Tile> {
    val result = mutableListOf<Tile>()
    var tile = this
    result.add(this)
    repeat(3) {
        tile = tile.rotateClockwise()
        result.add(tile)
    }
    tile = tile.flipHorizontally()
    result.add(tile)
    repeat(3) {
        tile = tile.rotateClockwise()
        result.add(tile)
    }
    return result.reversed()
}

private fun combineGrid(grid: Grid): Tile {
    return (0 until GRID_HEIGHT).flatMap { y ->
        (1 until TILE_HEIGHT - 1).map { ty ->
            (0 until GRID_WIDTH).joinToString("") { x ->
                val tile = grid[x, y]
                tile.second[ty].substring(1, TILE_WIDTH - 1)
            }
        }
    }
}

private fun findSeaMonsters(tile: Tile): List<Pair<Int, Int>> {
    val width = tile[0].length
    val height = tile.size
    return (0 until width).flatMap { x ->
        (0 until height).mapNotNull { y ->
            if (SEA_MONSTER_OFFSETS.all { (ox, oy) -> tile[x + ox, y + oy] == '#' }) {
                Pair(x, y)
            } else null
        }
    }
}
