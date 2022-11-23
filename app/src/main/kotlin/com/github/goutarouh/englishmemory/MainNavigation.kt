package com.github.goutarouh.englishmemory

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
    val mainNavController = MainNavController(navController)
    NavHost(navController = navController, startDestination = Dest.Home.root) {
        composable(Dest.Home.root) {
            HomeScreen(
                onAutoPlayButtonClicked = {
                    mainNavController.navigate(Dest.AutoPlay)
                }
            )
        }
        composable(Dest.AutoPlay.root) {
            AutoPlayScreen()
        }
    }


}

class MainNavController(
    private val navController: NavHostController
) {
    fun navigate(dest: Dest) {
        if (navController.currentDestination?.route != dest.root) {
            navController.navigate(dest.root)
        }
    }
}