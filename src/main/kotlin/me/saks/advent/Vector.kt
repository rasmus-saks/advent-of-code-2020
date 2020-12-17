package me.saks.advent

@Suppress("UNCHECKED_CAST")
fun <T> fromComponents(components: List<Int>): Vector<T> {
    if (components.size == 3) {
        return Vector3(components[0], components[1], components[2]) as Vector<T>
    }
    if (components.size == 4) {
        return Vector4(components[0], components[1], components[2], components[3]) as Vector<T>
    }
    throw IllegalArgumentException(components.toString())
}

interface Vector<T> : Comparable<Vector<T>> {
    operator fun rangeTo(other: Vector<T>): List<Vector<T>> {
        val (min, max) = listOf(this, other).bounds()
        val minC = min.components()
        val maxC = max.components()
        val result = mutableListOf<Vector<T>>()
        forComponents(minC, maxC) {
            result.add(fromComponents(it))
        }
        return result
    }
    override fun compareTo(other: Vector<T>): Int {
        return components().sum().compareTo(other.components().sum())
    }
    operator fun plus(other: Int): Vector<T>
    operator fun minus(other: Int): Vector<T>
    fun components(): List<Int>
    fun neighbors(): List<Vector<T>>
}

private fun forComponents(min: List<Int>, max: List<Int>, action: (List<Int>) -> Unit) {
    if (min.isEmpty() || max.isEmpty()) return
    (min[0]..max[0]).forEach { v ->
        forComponents(min.drop(1), max.drop(1), listOf(v), action)
    }
}
private fun forComponents(min: List<Int>, max: List<Int>, values: List<Int>, action: (List<Int>) -> Unit) {
    if (min.isEmpty() || max.isEmpty()) {
        action(values)
        return
    }
    (min[0]..max[0]).forEach { v ->
        forComponents(min.drop(1), max.drop(1), values + v, action)
    }
}

data class Vector3(val x: Int, val y: Int, val z: Int) : Vector<Vector3> {

    override fun components(): List<Int> {
        return listOf(x, y, z)
    }

    operator fun plus(other: Vector3): Vector3 {
        return Vector3(x + other.x, y + other.y, z + other.z)
    }

    override operator fun plus(other: Int): Vector3 {
        return Vector3(x + other, y + other, z + other)
    }

    operator fun minus(other: Vector3): Vector3 {
        return Vector3(x - other.x, y - other.y, z - other.z)
    }

    override operator fun minus(other: Int): Vector3 {
        return Vector3(x - other, y - other, z - other)
    }

    override fun neighbors(): List<Vector<Vector3>> {
        return (this - UNIT..this + UNIT).filter { it != this }
    }

    companion object {
        val UNIT = Vector3(1, 1, 1)
    }
}

data class Vector4(val x: Int, val y: Int, val z: Int, val w: Int) : Vector<Vector4> {
    override fun components(): List<Int> {
        return listOf(x, y, z, w)
    }

    operator fun plus(other: Vector4): Vector4 {
        return Vector4(x + other.x, y + other.y, z + other.z, w + other.w)
    }

    override operator fun plus(other: Int): Vector4 {
        return Vector4(x + other, y + other, z + other, w + other)
    }

    operator fun minus(other: Vector4): Vector4 {
        return Vector4(x - other.x, y - other.y, z - other.z, w - other.w)
    }

    override operator fun minus(other: Int): Vector4 {
        return Vector4(x - other, y - other, z - other, w - other)
    }

    override fun neighbors(): List<Vector<Vector4>> {
        return (this - UNIT..this + UNIT).filter { it != this }
    }

    companion object {
        val UNIT = Vector4(1, 1, 1, 1)
    }
}

fun <T> List<Vector<T>>.bounds(): Pair<Vector<T>, Vector<T>> {
    val min = this[0].components().toMutableList()
    val max = this[0].components().toMutableList()
    this.forEach {
        it.components().mapIndexed { i, c ->
            if (c < min[i]) min[i] = c
            if (c > max[i]) max[i] = c
        }
    }
    return Pair(fromComponents(min), fromComponents(max))
}
