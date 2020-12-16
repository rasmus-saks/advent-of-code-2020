package me.saks.advent

val RULE = Regex("""(?<name>[a-z ]+): (?<r1min>\d+)-(?<r1max>\d+) or (?<r2min>\d+)-(?<r2max>\d+)""")


fun main() {
    val (rulesStr, myTicketStr, nearbyTicketsStr) =
        "day16.txt"
            .readInputSplitBy("\n\n")
    val rules = rulesStr
        .split("\n")
        .map {
            RULE.matchEntire(it)!!.let { m ->
                Rule(m["name"], m["r1min"].toInt()..m["r1max"].toInt(), m["r2min"].toInt()..m["r2max"].toInt())
            }
        }
    val myTicket = myTicketStr.split("\n")[1].split(",").map { it.toInt() }
    val tickets = nearbyTicketsStr.split("\n").drop(1).map { ArrayList(it.split(",").map { v -> v.toInt() }) }

    tickets
        .flatMap {
            it.filter { v -> rules.none { r -> r.isValid(v) } }
        }
        .sum()
        .partOneSolution()
    val mappings = rules.map { it.name to tickets[0].indices.toMutableSet() }.toMap()
    tickets
        .filter { t -> t.all { v -> rules.any { r -> r.isValid(v) } } }
        .forEach { t ->
            t.forEachIndexed { idx, v ->
                rules.filter { r -> !r.isValid(v) }
                    .forEach { r ->
                        mappings[r.name]!!.let { p ->
                            if (p.contains(idx)) {
                                p -= idx
                            }
                        }
                    }
            }
        }
    while (!mappings.all { it.value.size == 1 }) {
        mappings.filter { it.value.size == 1 }
            .forEach { single ->
                mappings
                    .filter { m -> m.value.size > 1 }
                    .forEach { m -> m.value -= single.value.single() }
            }
    }
    mappings
        .filter { (key, _) -> key.startsWith("departure") }
        .map { (_, value) -> myTicket[value.single()].toLong() }
        .reduce { acc, v -> acc * v }
        .partTwoSolution()
}

data class Rule(val name: String, val range1: IntRange, val range2: IntRange) {
    fun isValid(value: Int): Boolean {
        return value in range1 || value in range2
    }
}
