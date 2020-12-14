package me.saks.advent

val MASK = Regex("""mask = (?<mask>[X01]{36})""")
val MEM = Regex("""mem\[(?<addr>\d+)] = (?<val>\d+)""")

val MEMORY = mutableMapOf<Long, Long>()

fun main() {
    var mask = "X".repeat(36)
    "day14.txt".readInputLines()
        .forEach { line ->
            MASK.matchEntire(line)?.let {
                mask = it["mask"]
            }
            MEM.matchEntire(line)?.let {
                MEMORY[it["addr"].toLong()] = applyMask(mask, it["val"])
            }
        }
    MEMORY.values.sum().partOneSolution()

    MEMORY.clear()

    "day14.txt".readInputLines()
        .forEach { line ->
            MASK.matchEntire(line)?.let {
                mask = it["mask"]
            }
            MEM.matchEntire(line)?.let {
                allAddressMasks(mask)
                    .forEach { addrMask ->
                        MEMORY[applyMask(addrMask, it["addr"])] = it["val"].toLong()
                    }
            }
        }
    MEMORY.values.sum().partTwoSolution()
}

fun applyMask(mask: String, value: String): Long {
    return value.toLong()
        .or(mask.replace('X', '0').toLong(2))
        .and(mask.replace('X', '1').toLong(2))
}

fun allAddressMasks(mask: String): Sequence<String> {
    return sequence {
        if (mask.isEmpty()) {
            yield("")
            return@sequence
        }
        allAddressMasks(mask.substring(1)).forEach {
            when (mask[0]) {
                '0' -> yield("X$it")
                '1' -> yield("1$it")
                'X' -> {
                    yield("0$it")
                    yield("1$it")
                }
            }
        }
    }
}
