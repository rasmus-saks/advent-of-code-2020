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

operator fun <A, B> Pair<List<A>, List<B>>.plus(other: Pair<A, B>): Pair<List<A>, List<B>> {
    return Pair(this.first + other.first, this.second + other.second)
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

fun <T> List<T>.replace(index: Int, newValue: T): List<T> {
    return this.take(index) + newValue + this.takeLast(this.size - index - 1)
}

fun chineseRemainder(n: List<Long>, a: List<Long>): Long {
    var sum = 0L
    val prod = n.reduce { acc, l -> acc * l }
    n.zip(a).forEach { (n2, a2) ->
        val p = prod / n2
        sum += a2 * multInv(p, n2) * p
    }
    return sum % prod
}

private fun multInv(a: Long, b: Long): Long {
    if (b == 1L) return 1
    var aa = a
    var bb = b
    var x0 = 0L
    var x1 = 1L
    while (aa > 1) {
        val q = aa / bb
        var t = bb
        bb = aa % bb
        aa = t
        t = x0
        x0 = x1 - q * x0
        x1 = t
    }
    if (x1 < 0) x1 += b
    return x1
}
