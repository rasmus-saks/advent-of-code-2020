package me.saks.advent.aoc2020

import me.saks.advent.helpers.*

val INSTRUCTION = Regex("""(?<ins>\w{3}) (?<arg>[+-]\d+)""")

data class Environment(val pc: Int = 0, val acc: Int = 0)

private val OPERATIONS = mapOf<String, (Environment, Int) -> Environment>(
    "acc" to { e, arg -> e.copy(pc = e.pc + 1, acc = e.acc + arg) },
    "jmp" to { e, arg -> e.copy(pc = e.pc + arg, acc = e.acc) },
    "nop" to { e, _ -> e.copy(pc = e.pc + 1, acc = e.acc) },
)

sealed class RunResult(val env: Environment) {
    class Loops(env: Environment, val loop: List<Int>) : RunResult(env)
    class Exits(env: Environment) : RunResult(env)
}

fun main() {
    val lines = "2020/08.txt".readInputLines()
    val baseRun = runProgram(lines) as RunResult.Loops
    baseRun.env.acc.partOneSolution()

    baseRun.loop.forEach {
        val newLines = lines.replace(it, flipInstruction(lines[it]))
        val result = runProgram(newLines)
        if (result is RunResult.Exits) {
            result.env.acc.partTwoSolution()
            return
        }
    }
}

fun flipInstruction(line: String): String {
    return if (line.startsWith("jmp")) line.replace("jmp", "nop") else line.replace("nop", "jmp")
}


fun runProgram(lines: List<String>): RunResult {
    val instructions = lines
        .map { INSTRUCTION.matchEntire(it) }
        .map { Pair(it["ins"], it["arg"].toInt()) }
    val history = mutableListOf<Int>()
    var env = Environment()
    while (!history.contains(env.pc) && env.pc < instructions.size) {
        history.add(env.pc)
        val instruction = instructions[env.pc]
        env = OPERATIONS.getValue(instruction.first)(env, instruction.second)
    }
    return if (env.pc >= instructions.size) {
        RunResult.Exits(env)
    } else {
        RunResult.Loops(env, history.subList(history.indexOf(env.pc), history.size))
    }
}
