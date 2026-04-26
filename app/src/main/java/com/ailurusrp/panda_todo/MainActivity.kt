package com.ailurusrp.panda_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ailurusrp.panda_todo.common.ui.LocalSnackbarHostState
import com.ailurusrp.panda_todo.features.dailyreport.ui.DailyReportScreen
import com.ailurusrp.panda_todo.features.home.ui.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PandaTodo()
        }
    }
}

@Composable
fun PandaTodo() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        NavHost(
            navController = navController,
            startDestination = "home",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable("home") { HomeScreen(navController) }
            composable("daily_report") { DailyReportScreen(navController) }
        }
    }
}
