package com.htr.htrr.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.htr.htrr.presentation.screen.DetailScreen
import com.htr.htrr.presentation.screen.HomeScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HomeRoute.route
    ) {
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