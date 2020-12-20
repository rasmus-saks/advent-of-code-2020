package me.saks.advent.aoc2020

import me.saks.advent.helpers.partOneSolution
import me.saks.advent.helpers.partTwoSolution
import me.saks.advent.helpers.readInputSplitBy

private val RULES = mutableMapOf<Int, MessageRule>()

fun main() {
    run {
        val (unparsed, messages) = "2020/19.txt"
            .readInputSplitBy("\n\n")
            .map { it.split("\n") }

        RULES.putAll(
            unparsed
                .map { it.split(": ") }
                .map { (index, rule) -> index.toInt() to parseRule(rule, RULES) }
        )

        messages
            .count { RULES[0]!!(it).any { match -> match.isBlank() }}
            .partOneSolution()
        RULES.clear()
    }
    run {
        val (unparsed, messages) = "2020/09-2.txt"
            .readInputSplitBy("\n\n")
            .map { it.split("\n") }

        RULES.putAll(
            unparsed
                .map { it.split(": ") }
                .map { (index, rule) -> index.toInt() to parseRule(rule, RULES) }
        )

        messages
            .count { RULES[0]!!(it).any { match -> match.isBlank() }}
            .partTwoSolution()
    }
}

private fun parseRule(rule: String, rules: Map<Int, MessageRule>): MessageRule {
    return when {
        rule.startsWith('"') -> MessageRule.Literal(rule[1])
        rule.contains("|") -> MessageRule.Or(rule.split(" | ").map { parseRule(it, rules) })
        else -> MessageRule.RuleSequence(rule.split(" ").map { MessageRule.Reference(it.toInt(), rules) })
    }
}

private sealed class MessageRule {
    abstract operator fun invoke(message: String): Sequence<String>

    data class Or(val rules: List<MessageRule>) : MessageRule() {
        override fun invoke(message: String): Sequence<String> {
            return rules.asSequence().flatMap { it(message) }
        }
    }

    data class RuleSequence(val rules: List<MessageRule>) : MessageRule() {
        private fun invokeInner(message: String, idx: Int): Sequence<String> {
            if (idx > rules.lastIndex) return sequenceOf(message)
            val matches = rules[idx](message)
            return matches.asSequence().flatMap { invokeInner(it, idx + 1) }
        }

        override fun invoke(message: String): Sequence<String> {
            return invokeInner(message, 0)
        }
    }

    data class Literal(val value: Char) : MessageRule() {
        override fun invoke(message: String): Sequence<String> {
            if (message.isEmpty()) return emptySequence()
            return if (message.startsWith(value)) {
                sequenceOf(message.drop(1))
            } else emptySequence()
        }
    }

    data class Reference(val index: Int, val rules: Map<Int, MessageRule>) : MessageRule() {
        override fun invoke(message: String): Sequence<String> {
            return rules[index]!!(message)
        }
    }
}
