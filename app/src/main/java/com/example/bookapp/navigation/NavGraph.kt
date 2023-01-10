package com.example.bookapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bookapp.presentation.screens.category.CategoryScreen
import com.example.bookapp.presentation.screens.details.DetailsScreen
import com.example.bookapp.presentation.screens.detailsjetpack.JetpackDetailsScreen
import com.example.bookapp.presentation.screens.home.HomeScreen
import com.example.bookapp.presentation.screens.jetpack.JetpackScreen
import com.example.bookapp.presentation.screens.jetpacksearch.JetpackSearchScreen
import com.example.bookapp.presentation.screens.search.SearchScreen
import com.example.bookapp.presentation.screens.splash.SplashScreen
import com.example.bookapp.presentation.screens.welcome.WelcomeScreen
import com.example.bookapp.presentation.screens.xml.XmlScreen

import com.example.bookapp.presentation.screens.xmldetails.XmlDetailsScreen
import com.example.bookapp.util.Constants.DETAILS_ARGUMENT_KEY

import com.example.bookapp.util.Constants.DETAILS_XML_KEY
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        //Kotlin
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(route = Screen.Category.route) {
            CategoryScreen(navHostController = navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailsScreen(navHostController = navController)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navHostController = navController)
        }
        //
        //
        //
        //
        //SCREEN JETPACK
        composable(route = Screen.Jetpack.route) {
            JetpackScreen(navHostController = navController)
        }

        composable(
            route = Screen.DetailsJetpack.route,
            arguments = listOf(navArgument("jetId") {
                type = NavType.IntType
            })
        ) {
            JetpackDetailsScreen(navHostController = navController)
        }
        composable(route = Screen.JetpackSearch.route) {
            JetpackSearchScreen(navHostController = navController)
        }


        //
        //
        //
        //
        //XML
        composable(route = Screen.XmlScreen.route) {
            XmlScreen(navHostController = navController)
        }
        //xml details screen
        composable(
            route = Screen.DetailsXml.route,
            arguments = listOf(navArgument(DETAILS_XML_KEY) {
                type = NavType.IntType
            })
        ) {
            XmlDetailsScreen(navHostController = navController)
        }



    }
}