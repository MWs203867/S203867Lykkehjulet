package com.example.s203867lykkehjulet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.s203867lykkehjulet.ui.theme.S203867LykkehjuletTheme
/*The overall architecure of the app is inspired by the codelab Unscramble app made by googledevelopertraining
and can be found here:
*  https://github.com/google-developer-training/android-basics-kotlin-unscramble-app/tree/main/app/src/main/java/com/example/android/unscramble/ui/game
the class gamescreen and all their functions are modified versions of the unscramble and therefore
it looks similiar to that, wordata file and the  idea to use such a data file was also taken from
the unscramble although it's vastly different*/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            S203867LykkehjuletTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GameScreen()

                }
            }
        }
    }


}