package me.saks.advent.helpers

fun <T> List<List<T>>.flipHorizontally(): List<List<T>> {
    return this.map { it.reversed() }
}

fun <T> List<List<T>>.rotateClockwise(): List<List<T>> {
    val oldWidth = this[0].size
    val oldHeight = this.size
    return (0 until oldWidth).map { newY ->
        (0 until oldHeight).map { newX ->
            this[oldHeight - newX - 1][newY]
        }
    }
}

@JvmName("flipHorizontallyString")
fun List<String>.flipHorizontally(): List<String> {
    return this.map { it.reversed() }
}

@JvmName("rotateClockwiseString")
fun List<String>.rotateClockwise(): List<String> {
    val oldWidth = this[0].length
    val oldHeight = this.size
    return (0 until oldWidth).map { newY ->
        (0 until oldHeight).map { newX ->
            this[oldHeight - newX - 1][newY]
        }.joinToString("")
    }
}

fun <T> List<T>.rest(): List<T> {
    return this.drop(1)
}

fun List<String>.column(index: Int): String {
    return this.map { it[index] }.joinToString("")
}
