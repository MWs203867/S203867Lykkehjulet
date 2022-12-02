package com.example.s203867lykkehjulet

data class GameUiState(
    val currentHiddenWord: String ="",
    val currentShowedWord: String = "",
    val currentCategory: String ="",
    val currentWronglyGuessedLetters: String = "",
    val score: Int = 0,
    val lives: Int = 5,
    val isGuessedLetterWrong: Boolean= false,
    val isGameOver: Boolean = false,
    val duplicateGuess: Boolean = false,
    val spinValue: Int = 1,
    val hasSpinned: Boolean = false,


)