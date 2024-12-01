package com.example.userlookup.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.userlookup.ui.theme.primaryButtomColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun showSnackBar(coroutineScope: CoroutineScope, snackState: SnackbarHostState, message:String) {
    coroutineScope.launch {
        launchSnackBar(snackState,message)
    }
}
suspend fun launchSnackBar(snackState: SnackbarHostState, message:String)
{
    snackState.showSnackbar(
        message = message,
        duration = SnackbarDuration.Short
    )
}

@Composable
fun SnackBar(snackState: SnackbarHostState)
{
    Box(modifier = Modifier.fillMaxSize(), Alignment.BottomCenter) {

        SnackbarHost(hostState = snackState) {
            Snackbar(
                snackbarData = it,
                containerColor = Color.Black,
                contentColor = Color.White
            )
        }
    }
}

@Composable
fun ShowProgress(strokeThickNess: Dp,indicatorWidth:Dp) {
    CircularProgressIndicator(
        modifier = Modifier.width(indicatorWidth).padding(vertical = 20.dp),
        color = primaryButtomColor,
        strokeWidth=strokeThickNess
    )
}