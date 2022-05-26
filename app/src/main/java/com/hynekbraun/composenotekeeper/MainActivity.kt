package com.hynekbraun.composenotekeeper

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hynekbraun.composenotekeeper.presentation.Navigation
import com.hynekbraun.composenotekeeper.ui.theme.BackgroundRed
import com.hynekbraun.composenotekeeper.ui.theme.ComposeNoteKeeperTheme
import com.hynekbraun.composenotekeeper.ui.theme.md_theme_dark_primary
import com.hynekbraun.composenotekeeper.ui.theme.md_theme_light_primary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNoteKeeperTheme{
                val systemUiController = rememberSystemUiController()
                if(isSystemInDarkTheme()){
                    systemUiController.setSystemBarsColor(
                        color = md_theme_dark_primary
                    )
                }else{
                    systemUiController.setSystemBarsColor(
                        color = md_theme_light_primary
                    )
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Navigation()
                }
            }
        }
    }
}
