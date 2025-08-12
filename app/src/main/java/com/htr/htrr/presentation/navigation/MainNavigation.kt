package com.htr.htrr.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.htr.htrr.presentation.screen.DetailScreen
import com.htr.htrr.presentation.screen.HomeScreen
import com.htr.htrr.presentation.screen.SplashScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SplashScreen.route
    ) {
        composable(
            route = NavRoutes.SplashScreen.route,
        ) {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(NavRoutes.HomeRoute.route) {
                        popUpTo(NavRoutes.SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = NavRoutes.HomeRoute.route,
        ) { _ -> HomeScreen() }

        composable(
            route = NavRoutes.DetailRoute.route,
            arguments = listOf(navArgument(NavArgsKey.ITEM_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val itemId = NavRoutes.DetailRoute.getId(backStackEntry)

            itemId?.let {
                DetailScreen(itemId = it)
            }
        }
    }
}