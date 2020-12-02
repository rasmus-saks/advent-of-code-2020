fun main() {
    "day1.txt".readInputLines()
        .toInt()
        .allPairs()
        .find { (a, b) -> a + b == 2020 }
        ?.let { (a, b) -> a * b }
        .partOneSolution()

    "day1.txt".readInputLines()
        .toInt()
        .allTriples()
        .find { (a, b, c) -> a + b + c == 2020 }
        ?.let { (a, b, c) -> a * b * c }
        .partTwoSolution()
}
