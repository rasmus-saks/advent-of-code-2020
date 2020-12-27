package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.print
import me.saks.advent.helpers.readInputLines
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

private val SEVEN = 7.toBigInteger()
private val MOD = 20201227.toBigInteger()

fun main() {
    val (cardPub, doorPub) = "2020/25.txt"
        .readInputLines()
        .map { it.toBigInteger() }
    val cardLoops = findLoopSize(SEVEN, cardPub).print()
//    val doorLoops = findLoopSize(SEVEN, doorPub).print()
    doorPub.modPow(cardLoops, MOD).partOneSolution()
//    cardPub.modPow(doorLoops, MOD).partOneSolution()
}

fun findLoopSize(subject: BigInteger, result: BigInteger): BigInteger {
    return (generateSequence(ZERO) { it.plus(ONE) }).first { subject.modPow(it, MOD) == result }
}
