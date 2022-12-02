package com.example.s203867lykkehjulet


import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
/*All these functions are modified version of the unscramble app in the codelab*/
@Composable
fun GameScreen(
    modifier:
    Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = {
                    gameViewModel.resetGame()
                }
            )
        }
        GameStatus(
            lives = gameUiState.lives,
            score = gameUiState.score,
            letters = gameUiState.currentWronglyGuessedLetters
        )
        GameLayout(
            onUserGuessChanged = { gameViewModel.updateUsergues(it) },
            userGuess = gameViewModel.userGuess,
            onKeyboardDone = { gameViewModel.checkUserguess() },
            currentShowedWord = gameUiState.currentShowedWord,
            isGuessedLetterWrong = gameUiState.isGuessedLetterWrong,
            duplicateGuess = gameUiState.duplicateGuess,
            currentCategory = gameUiState.currentCategory
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (!gameUiState.hasSpinned) {
                OutlinedButton(
                    onClick = { gameViewModel.spin() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    if (gameUiState.spinValue != 0) {
                        Text(text = stringResource(R.string.spin))
                    } else {

                        Text(text = stringResource(id = R.string.bankrupt))
                    }
                }
            } else {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = gameUiState.spinValue.toString())
                }
                if (!gameUiState.hasSpinned) {
                    Button(
                        onClick = { },
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(start = 8.dp),
                    ) {
                        Text(text = stringResource(id = R.string.spinbeforeguess))


                    }

                } else {
                    Button(
                        onClick = { gameViewModel.checkUserguess() },
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(start = 8.dp),
                    ) {
                        Text(text = stringResource(id = R.string.guess))
                    }
                }


            }
        }
    }
}


@Composable
fun FinalScoreDialog(score: Int, onPlayAgain: () -> Unit, modifier: Modifier = Modifier) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(onDismissRequest = {},
        title = { Text(stringResource(id = R.string.gameover)) },
        text = { Text(stringResource(id = R.string.finalScore, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = { activity.finish() }
            ) {
                Text(text = stringResource(id = R.string.exit))
            }

        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(id = R.string.playagain))

            }
        })


}

@Composable
fun GameLayout(
    currentShowedWord: String,
    currentCategory: String,
    isGuessedLetterWrong: Boolean,
    duplicateGuess: Boolean,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp))
    {
        Text(
            text = currentShowedWord,
            fontSize = 65.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = currentCategory,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.oneletteratatime),
            fontSize = 17.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


        OutlinedTextField(
            value = userGuess,
            singleLine = true,
            onValueChange = onUserGuessChanged,
            label = {

                if (duplicateGuess) {
                    Text(text = stringResource(id = R.string.duplicateletter))
                } else if (isGuessedLetterWrong) {
                    Text(text = stringResource(id = R.string.wrongGuess))

                } else {
                    Text(text = stringResource(id = R.string.typehere))
                }
            },
            isError = isGuessedLetterWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }),

            )


    }

}

@Composable
fun GameStatus(lives: Int, score: Int, letters: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(46.dp),
    ) {
        Text(
            text = stringResource(id = R.string.wrongletters) + letters,
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "\t\t\t\t" + stringResource(id = R.string.lives, lives),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(id = R.string.score, score),
            fontSize = 18.sp,
        )

    }

}

