package com.example.pennassessment.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pennassessment.R
import com.example.pennassessment.ui.theme.PennAssessmentTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class LaunchActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PennAssessmentTheme {
                SplashScreen()
            }
        }
    }

    /**
     * A very basic splash screen
     */
    @Preview
    @Composable
    private fun SplashScreen(){
        /**
         * Ultimately I would want to load any initial information here, so it's displayed once the main activity starts. The delay would be simulating the time needed to load
         * information from server. I had already designed the app around using buttons to fetch information, but this would be the better approach for the user's information (location,
         * nearest station, etc.)
         */
        LaunchedEffect(key1 = true) {
            delay(2500)
            startActivity(Intent(this@LaunchActivity, MainActivity::class.java))

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center

        ){
            Image(
                painter = painterResource(id = R.drawable.weatherpng),
                contentDescription = null
            )
        }
    }
}