package com.example.userlookup.lookupfeature.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.userlookup.R
import com.example.userlookup.lookupfeature.presentation.UserLookUpViewModel
import com.example.userlookup.lookupfeature.presentation.util.UserLookupUIState
import com.example.userlookup.ui.common.ShowProgress
import com.example.userlookup.ui.common.SnackBar
import com.example.userlookup.ui.common.showSnackBar
import com.example.userlookup.ui.theme.primaryBaseColor
import com.example.userlookup.ui.theme.primaryButtomColor
import com.example.userlookup.util.NetworkUtil
import com.example.userlookup.util.Routes

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun UserLookUpScreen(lookUpViewModel: UserLookUpViewModel,navController:NavController)
{

    val coroutineScope = rememberCoroutineScope()
    val snackState = remember { SnackbarHostState() }
    var text by remember { mutableStateOf("") }
    val viewDetailsMessage = stringResource(id = R.string.user_verification_message)
    val keyboardController = LocalSoftwareKeyboardController.current

    val uiState = lookUpViewModel.userlookUpUIState
    when (uiState) {
        UserLookupUIState.Loading ->{}
        UserLookupUIState.NoData -> {
            showSnackBar(coroutineScope,snackState,stringResource(id = R.string.user_not_found))
            lookUpViewModel.setUserLookupStateToInitial()
        }

        is  UserLookupUIState.Error -> {
            showSnackBar(coroutineScope,snackState,uiState.message)
            lookUpViewModel.setUserLookupStateToInitial()
        }

        is UserLookupUIState.GotData -> {
            navController.navigate(Routes.LOOKUP_DETAIL_SCREEN)
            lookUpViewModel.setUserLookupStateToInitial()
        }
        else -> {}
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBaseColor
    ) {

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            Card(
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.user_lookup), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        maxLines = 1,
                        enabled = uiState != UserLookupUIState.Loading,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if(text.isEmpty()) {
                                    showSnackBar(coroutineScope,snackState,viewDetailsMessage)
                                }
                                else {
                                    lookUpViewModel.fetchUser(text)
                                }
                                keyboardController?.hide()
                            }
                        ),
                        label = { Text(text= stringResource(id = R.string.user_name)) }
                    )

                    if(uiState == UserLookupUIState.Loading) {
                        ShowProgress(strokeThickNess = 3.dp, indicatorWidth = 20.dp)
                    }
                    else{
                        Button(onClick = {
                            if(text.isEmpty()) {
                                showSnackBar(coroutineScope,snackState,viewDetailsMessage)
                            }
                            else {
                                lookUpViewModel.fetchUser(text)
                            }
                            keyboardController?.hide()
                        }, colors = outlinedButtonColors(containerColor = primaryButtomColor, contentColor = Color.White),
                            modifier = Modifier.padding(top =20.dp, bottom = 10.dp),
                        ) {
                            Text(text= stringResource(id = R.string.view_Detail))
                        }
                    }
                }
            }

        }
    }

    SnackBar(snackState)
}