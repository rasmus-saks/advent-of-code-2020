package me.saks.advent

val BAG_REGEX = Regex("""(?<num>\d+) (?<color>[a-z]+ [a-z]+) bags?""")

fun main() {
    val rules = "day07.txt"
        .readInputSplitBy(".\n")
        .map { it.split(" bags contain ").toPair() }
        .map { sp ->
            sp.first to sp.second.split(", ").flatMap {
                val match = BAG_REGEX.matchEntire(it) ?: return@flatMap listOf()
                return@flatMap List(match["num"].toInt()) { match["color"] }
            }
        }
        .toMap()
    distinctOuterBags(rules, "shiny gold")
        .size
        .partOneSolution()

    requiredInnerBags(rules, "shiny gold")
        .partTwoSolution()
}

fun distinctOuterBags(rules: Map<String, List<String>>, inner: String): Set<String> {
    return rules.filter { it.value.contains(inner) }.keys.flatMap { setOf(it) + distinctOuterBags(rules, it) }.toSet()
}

fun requiredInnerBags(rules: Map<String, List<String>>, outer: String): Int {
    return rules.getValue(outer).sumOf { 1 + requiredInnerBags(rules, it) }
}
