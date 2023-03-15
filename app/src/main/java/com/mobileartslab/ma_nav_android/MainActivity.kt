package com.mobileartslab.ma_nav_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobileartslab.ma_nav_android.ui.theme.ManavandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManavandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screens.Splash) {
                        composable(route = Screens.Splash) {
                            SplashScreen(navController = navController)
                        }

                        composable(route = Screens.Login) {
                            LoginScreen(navController = navController)
                        }

                        composable(route = Screens.Dashboard) {
                            DashBoardScreen(navController = navController)
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun Navigation() {

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ManavandroidTheme {
        Greeting("Android")
    }
}