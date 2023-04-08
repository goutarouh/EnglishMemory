package com.github.goutarouh.englishmemory

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.goutarouh.englishmemory.autoplay.AutoPlayScreen
import com.github.goutarouh.englishmemory.home.HomeScreen
import com.github.goutarouh.englishmemory.setting.SettingScreen


enum class Dest(val root: String) {
    Home("home"),
    AutoPlay("autoplay"),
    Setting("setting"),
}

@Composable
fun MainNavigation() {

    val navController = rememberNavController()
    val mainNavController = MainNavController(navController)
    NavHost(navController = navController, startDestination = Dest.Home.root) {
        composable(Dest.Home.root) {
            HomeScreen(
                onSettingButtonClicked = {
                    mainNavController.navigate(Dest.Setting)
                }
            )
        }
        composable(Dest.AutoPlay.root) {
            AutoPlayScreen(
                onAutoPlayEnd = {
                    mainNavController.navigateToTop()
                }
            )
        }
        composable(Dest.Setting.root) {
            SettingScreen()
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

    fun navigateToTop() {
        if (navController.currentDestination?.route != Dest.Home.root) {
            navController.navigate(Dest.Home.root) {
                popUpTo(Dest.Home.root) {
                    inclusive = true
                }
            }
        }
    }
}