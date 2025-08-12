package com.htr.htrr.presentation.navigation

import androidx.navigation.NavBackStackEntry

object NavArgsKey{
    const val ITEM_ID = "itemID"
}

sealed class NavRoutes(val route: String) {
    data object SplashScreen: NavRoutes("splash")
    data object HomeRoute : NavRoutes("home")
    data object DetailRoute : NavRoutes("details/{${NavArgsKey.ITEM_ID}}") {
        fun createRoute(itemId: String): String {
            return route.replace("{${NavArgsKey.ITEM_ID}}", itemId.toNavArg())
        }

        fun getId(entry: NavBackStackEntry): String? {
            return entry.arguments?.getString(NavArgsKey.ITEM_ID)?.fromNavArg()
        }
    }
}