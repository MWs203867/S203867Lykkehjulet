package com.example.s203867lykkehjulet
//all words and values for the "wheel" to choose/land on

val animal: Set<String> = setOf("Animals", "dog", "pidgeon","lion", "Elephant", "seal", )
val furniture: Set<String> = setOf("Furniture","Chair", "table", "sofa", "lamp", "pot")
val dishes: Set<String> = setOf("Dishes","carbonarra", "Beefwellington", "Lasagne", "Sushi")
val cities: Set<String> = setOf("Cities","London", "paris", "berlin","copenhagen")
val categories: Set<Set<String>> = setOf(animal, furniture, dishes, cities)
val spinvalues: List<Int> = listOf(1000,1000,1000,1000,4000,3000,2000,5000,4000,2000,3000,2000,0)