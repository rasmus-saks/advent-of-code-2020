package me.saks.advent.aoc2020

import me.saks.advent.helpers.*

fun main() {
    val input = "2020/21.txt"
        .readInputLines()
        .map {
            val (ingredientsStr, allergensStr) = it.split(" (contains ")
            val ingredients = ingredientsStr.split(" ").toSet()
            val allergens = allergensStr.dropLast(1).split(", ").toSet()
            allergens to ingredients
        }

    val allIngredients = input.fold(setOf<String>()) { acc, (_, ingredients) -> acc union ingredients }

    val allergenToIngredients = input
        .flatMap { (allergens, ingredients) -> allergens.map { it to ingredients } }
        .fold(mapOf<String, List<Set<String>>>()) { acc, (allergen, ingredients) ->
            acc + (allergen to (acc.getOrDefault(allergen, listOf()).plusElement(ingredients)))
        }
        .mapValues { (_, ingredients) ->
            ingredients.reduce { acc, ingr -> acc intersect ingr }
        }

    val allergenic = allergenToIngredients.values.reduce { acc, set -> acc union set }
    val nonAllergenic = allIngredients - allergenic

    input
        .sumOf { (_, ingredients) -> nonAllergenic.count { nonAllergen -> ingredients.contains(nonAllergen) } }
        .partOneSolution()

    solveAllergens(allergenToIngredients)
        .entries
        .sortedBy { (allergen, _) -> allergen }
        .joinToString(",") { (_, ingredient) -> ingredient }
        .partTwoSolution()
}

fun solveAllergens(allergenToIngredients: Map<String, Set<String>>): Map<String, String> {
    val solution: MutableMap<String, String> = mutableMapOf()
    var allergens: Map<String, Set<String>> = allergenToIngredients
    while (allergens.any { (_, ingredients) -> ingredients.isNotEmpty() }) {
        val single = allergens.entries.first { (_, ingredients) -> ingredients.size == 1 }
        solution += single.key to single.value.single()
        allergens = allergens.mapValues { (_, ingredients) -> ingredients - single.value.single() }
    }
    return solution
}
