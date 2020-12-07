package me.saks.advent

import java.io.InputStreamReader

fun <T> Collection<T>.allPairs(): List<Pair<T, T>> {
    return this.flatMap { el1 ->
        this.map { el2 ->
            Pair(el1, el2)
        }
    }
}

fun <T> Collection<T>.allTriples(): List<Triple<T, T, T>> {
    return this.flatMap { el1 ->
        this.flatMap { el2 ->
            this.map { el3 ->
                Triple(el1, el2, el3)
            }
        }
    }
}

fun <T> List<T>.toPair(): Pair<T, T> {
    if (this.size != 2) {
        throw IllegalArgumentException("$this does not have size 2")
    }
    return Pair(this[0], this[1])
}

fun <T> List<T>.toTriple(): Triple<T, T, T> {
    if (this.size != 3) {
        throw IllegalArgumentException("$this does not have size 3")
    }
    return Triple(this[0], this[1], this[2])
}

operator fun <A, B, C> Pair<A, B>.plus(third: C): Triple<A, B, C> {
    return Triple(this.first, this.second, third)
}

fun Collection<String>.toInt(): List<Int> {
    return this.map { it.toInt() }
}

fun <T> T.partOneSolution(): T {
    println("Part 1 solution: $this")
    return this
}

fun <T> T.partTwoSolution(): T {
    println("Part 2 solution: $this")
    return this
}

fun <T> T.print(): T {
    println(this)
    return this
}

fun Int.between(min: Int, max: Int): Boolean {
    return this in min..max
}

operator fun MatchResult?.get(name: String): String {
    return this!!.groups[name]!!.value
}

fun String.readInputLines(): List<String> {
    return InputStreamReader(ClassLoader.getSystemResource(this).openStream()).use { stream ->
        stream.readLines().map { it.trim() }
    }
}

fun String.readInputSplitBy(delimiter: String): List<String> {
    return InputStreamReader(ClassLoader.getSystemResource(this).openStream()).use { stream ->
        stream.readText().split(delimiter).map { it.trim() }.filter { it.isNotEmpty() }
    }
}
