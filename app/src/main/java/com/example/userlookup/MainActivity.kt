package com.example.userlookup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.userlookup.lookupfeature.presentation.UserLookUpViewModel
import com.example.userlookup.lookupfeature.presentation.ui.UserDetailBaseScreen
import com.example.userlookup.lookupfeature.presentation.ui.UserLookUpScreen
import com.example.userlookup.util.Routes
import com.example.userlookup.ui.theme.UserLookupTheme
import com.example.userlookup.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    private lateinit var lookUpViewModel :UserLookUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lookUpViewModel = ViewModelProvider(this)[UserLookUpViewModel::class.java]
        setContent {

            UserLookupTheme {
                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()
                NavHost(navController = navController, startDestination = Routes.LOOKUP_SCREEN, builder ={
                    composable(Routes.LOOKUP_SCREEN){
                        UserLookUpScreen(lookUpViewModel,navController)

                    }
                    composable(Routes.LOOKUP_DETAIL_SCREEN){
                        UserDetailBaseScreen(lookUpViewModel)
                    }
                } )
                SideEffect {
                    systemUiController.setStatusBarColor(statusBarColor)
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserLookupTheme {
        Greeting("Android")
    }
}
