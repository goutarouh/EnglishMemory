package com.github.goutarouh.englishmemory

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.goutarouh.englishmemory.autoplay.AutoPlayScreen
import com.github.goutarouh.englishmemory.home.HomeScreen


enum class Dest(val root: String) {
    Home("home"),
    AutoPlay("autoplay")
}

@Composable
fun MainNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Dest.Home.root) {
        composable(Dest.Home.root) {
            HomeScreen(
                onAutoPlayButtonClicked = {
                    navController.navigate(Dest.AutoPlay.root)
                }
            )
        }
        composable(Dest.AutoPlay.root) {
            AutoPlayScreen()
        }
    }


}