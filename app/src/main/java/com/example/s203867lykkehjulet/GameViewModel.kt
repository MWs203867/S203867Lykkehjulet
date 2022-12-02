package com.example.s203867lykkehjulet

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")

    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var hiddenWord: String
    private var showWord: String = ""
    private lateinit var currentCategory: String
    private var catSet: Set<String> = mutableSetOf()
    private var usedLetters: MutableSet<Char> = mutableSetOf()
    private var score: Int = 1
    private var hasSpinned: Boolean = false
    private var spinvalue: Int = 0;

    init {
        resetGame()
    }

    fun resetGame() {
        this.hasSpinned = false

        this.score = 0
        this._uiState.value = GameUiState()
        this.usedLetters.clear()
        this.usedWords.clear()
        val tempCat = pickNewCategory()
        val tempword = pickNewWord()
        this._uiState.value =
            GameUiState(
                currentCategory = tempCat,
                currentHiddenWord = tempword,
                currentShowedWord = hideWord(tempword)
            )


    }

    fun updateUsergues(guessedLetter: String) {
        userGuess = guessedLetter

    }

    fun checkUserguess() {
        var chars = showWord.toCharArray()
        var hiddenchars = hiddenWord.toCharArray()
        var correct = 0
        if (chars.isEmpty() || hiddenchars.isEmpty() || userGuess.isEmpty()) {
            return
        }
        if (usedLetters.contains(userGuess[0])) {
            updateGameState(-2)
            return
        }

        for (i in 0..hiddenchars.size - 1) {
            if (userGuess[0].equals(hiddenchars[i], ignoreCase = true)) {
                chars[i] = userGuess[0].uppercaseChar()

                correct = 1
                showWord = String(chars)
                _uiState.update { currentState ->
                    currentState.copy(currentShowedWord = showWord)
                }


            }

        }
        if (correct == 0) {
            usedLetters.add(userGuess[0])
            updateGameState(0)
        } else {
            for (i in 1..correct) {
                updateGameState(updateScore(this.score))
            }
        }


    }
//modified function from unscramble app
    //function for updating to view and checking wheter the user is has any lives left
    private fun updateGameState(updatedScore: Int) {

        if (hiddenWord.uppercase() == showWord) {
            usedLetters.clear()
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedLetterWrong = false,
                    currentCategory = pickNewCategory(),
                    currentHiddenWord = pickNewWord(),
                    currentShowedWord = hideWord(hiddenWord),
                    score = updatedScore,
                    currentWronglyGuessedLetters = usedLetters.toString(),
                    hasSpinned = false
                )

            }


        } else if (updatedScore == -2) {
            _uiState.update { currentState ->
                currentState.copy(
                    duplicateGuess = true
                )

            }

        } else if (updatedScore == 0) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedLetterWrong = true,
                    lives = _uiState.value.lives.dec(),
                    currentWronglyGuessedLetters = usedLetters.toString(),
                    hasSpinned = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedLetterWrong = false,
                    score = updatedScore
                )

            }
        }
        if (_uiState.value.lives < 1) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedLetterWrong = true,
                    isGameOver = true

                )
            }
        }


    }

    private fun pickNewCategory(): String {
        this.catSet = categories.random()

        this.currentCategory = catSet.first()
        this.usedWords.add(currentCategory)

        return currentCategory

    }
    // the picknewword functions has a functionality for going indefinetely as a category is filled
    // it will then reset all used words so that we don't get any infinitely recursion.
    private fun pickNewWord(): String {
        this.hiddenWord = catSet.random()
        var j = 0
        for (i in catSet.indices){
            if (usedWords.contains(catSet.elementAt(i))){
                j++
            }
        }
        if(j== catSet.size){
            usedWords.clear()
        }
        usedWords.add(catSet.first())
        if (usedWords.contains(hiddenWord)) {
            pickNewWord()
        }
        usedWords.add(this.hiddenWord)



        return this.hiddenWord

    }

    private fun hideWord(hiddenWord: String): String {
        showWord = ""
        for (i in 0..hiddenWord.length - 1) {
            showWord += "_"
        }
        //_uiState.update { currentState -> currentState.copy(currentShowedWord = showWord) }
        return showWord
    }

    private fun updateScore(score: Int): Int {


        if (score == -1) {
            return -1
        } else if (score == -2) {
            return -2
        } else {
             this.score += spinvalue
            return this.score
        }


    }

    fun spin() {
        this.hasSpinned = true;
        spinvalue = spinvalues.random()

        if (spinvalue == 0) {
            _uiState.update { currentState ->
                currentState.copy(
                    spinValue = spinvalue,
                    hasSpinned = false,
                    score = 0,
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    spinValue = spinvalue,
                    hasSpinned = true,

                )
            }
        }
    }


}

