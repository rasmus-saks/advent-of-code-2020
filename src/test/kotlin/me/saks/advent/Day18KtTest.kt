package me.saks.advent

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day18KtTest : StringSpec({
    "part one" {
        forAll(
            row("1", "1", 1),
            row("10", "10", 10),
            row("1 + 1", "(1 + 1)", 2),
            row("1 + 1 + 1", "((1 + 1) + 1)", 3),
            row("1 + 1 * 2", "((1 + 1) * 2)", 4),
            row("1 + (1 + 1)", "(1 + (1 + 1))", 3),
            row("1 + ((1 + 1) + 1)", "(1 + ((1 + 1) + 1))", 4),
            row("2 * 3 + (4 * 5)", "((2 * 3) + (4 * 5))", 26),
            row("1 + (2 * 3) + (4 * (5 + 6))", "((1 + (2 * 3)) + (4 * (5 + 6)))", 51),
        ) { expression, parsed, result ->
            val actual = parse(expression, listOf("+*"))
            actual.toString() shouldBe parsed
            actual.evaluate() shouldBe result
        }
    }

    "part two" {
        forAll(
            row("1 * 1 + 2", "(1 * (1 + 2))", 3),
            row("2 * 3 + (4 * 5)", "(2 * (3 + (4 * 5)))", 46),
            row("1 + (2 * 3) + (4 * (5 + 6))", "((1 + (2 * 3)) + (4 * (5 + 6)))", 51),
            row(
                "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2",
                "((((((2 + 4) * 9) * (((6 + 9) * (8 + 6)) + 6)) + 2) + 4) * 2)",
                23340
            ),
        ) { expression, parsed, result ->
            val actual = parse(expression, listOf("+", "*"))
            actual.toString() shouldBe parsed
            actual.evaluate() shouldBe result
        }
    }
})
