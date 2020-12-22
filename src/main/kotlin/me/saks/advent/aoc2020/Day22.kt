package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputSplitBy

typealias Deck = List<Int>
typealias Player = Int

fun main() {
    val (player1, player2) = "2020/22.txt"
        .readInputSplitBy("\n\n")
        .map { p -> p.split("\n").drop(1).map { it.toInt() }.toMutableList() }

    val (_, winner1) = crabCombat(player1, player2)
    winner1
        .foldIndexed(0L) { idx, acc, c -> acc + c * (winner1.size - idx) }
        .partOneSolution()

    val (_, winner2) = recursiveCombat(player1, player2)
    winner2
        .foldIndexed(0L) { idx, acc, c -> acc + c * (winner2.size - idx) }
        .partTwoSolution()
}

fun crabCombat(p1: Deck, p2: Deck): Pair<Player, Deck> {
    val player1 = p1.toMutableList()
    val player2 = p2.toMutableList()
    while (player1.size != 0 && player2.size != 0) {
        val c1 = player1.removeFirst()
        val c2 = player2.removeFirst()
        if (c1 > c2) {
            player1.add(c1)
            player1.add(c2)
        } else {
            player2.add(c2)
            player2.add(c1)
        }
    }
    return if (player1.size != 0) Pair(1, player1) else Pair(2, player2)
}

fun recursiveCombat(p1: Deck, p2: Deck, h: Set<Pair<Deck, Deck>> = setOf(), depth: Int = 0): Pair<Player, Deck> {
    val hands = h.toMutableSet()
    var player1 = p1
    var player2 = p2
    while (true) {
        val hand = Pair(player1, player2)
        if (hands.contains(hand)) return Pair(1, player1)
        if (player1.isEmpty()) return Pair(2, player2)
        if (player2.isEmpty()) return Pair(1, player1)
        val c1 = player1.first()
        val c2 = player2.first()
        player1 = player1.drop(1)
        player2 = player2.drop(1)
        val winner: Player = when {
            player1.size >= c1 && player2.size >= c2 ->
                recursiveCombat(player1.take(c1), player2.take(c2), hands, depth + 1).first
            c1 > c2 -> 1
            else -> 2
        }
        when (winner) {
            1 -> player1 = player1 + c1 + c2
            2 -> player2 = player2 + c2 + c1
        }

        hands.add(hand)
    }
}
