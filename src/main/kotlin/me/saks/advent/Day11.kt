package me.saks.advent

fun main() {
    var board = "day11.txt"
        .readInputLines()
    var newBoard = frame(board)
    while (newBoard != board) {
        board = newBoard
        newBoard = frame(board)
    }
    board.sumOf { it.count { c -> c == '#'} }.partOneSolution()

    board = "day11.txt"
        .readInputLines()
    newBoard = frame(board, 100, 5)
    while (newBoard != board) {
        board = newBoard
        newBoard = frame(board, 100, 5)
    }
    board.sumOf { it.count { c -> c == '#'} }.partTwoSolution()
}

fun frame(board: List<String>, maxDist: Int = 1, minOccupied: Int = 4): List<String> {
    val newBoard: MutableList<CharArray> = board.map { it.toCharArray() }.toMutableList()
    board.forEachIndexed { y, row ->
        row.forEachIndexed { x, seat ->
            when (seat) {
                'L' -> if (countVisibleOccupied(x, y, board, maxDist) == 0) newBoard[y][x] = '#'
                '#' ->  if (countVisibleOccupied(x, y, board, maxDist) >= minOccupied) newBoard[y][x] = 'L'
            }
        }
    }
    return newBoard.map { String(it) }
}

fun countVisibleOccupied(x: Int, y: Int, board: List<String>, maxDist: Int): Int {
    return listOf(-1, 0, 1).sumOf { dy ->
        listOf(-1, 0, 1).count { dx ->
            if (dx == 0 && dy == 0) return@count false
            (1..maxDist).asSequence().map { board.getOrNull(y+dy*it)?.getOrNull(x+dx*it) }.firstOrNull { it != '.' } == '#'
        }
    }
}
