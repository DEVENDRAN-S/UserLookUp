package com.example.userlookup.lookupfeature.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.userlookup.R
import com.example.userlookup.lookupfeature.model.UserPost
import com.example.userlookup.lookupfeature.presentation.UserLookUpViewModel
import com.example.userlookup.lookupfeature.presentation.util.PostLookupUIState
import com.example.userlookup.ui.common.ShowProgress
import com.example.userlookup.ui.common.SnackBar
import com.example.userlookup.ui.common.showSnackBar
import com.example.userlookup.ui.theme.primaryBaseColor
import com.example.userlookup.ui.theme.primaryTextColor


@Composable
fun UserPageScreen(lookUpViewModel:UserLookUpViewModel) {

    val uiState = lookUpViewModel.postlookUpUIState
    val coroutineScope = rememberCoroutineScope()
    val snackState = remember { SnackbarHostState() }

    when (uiState) {
        PostLookupUIState.Loading -> {}
        PostLookupUIState.NoData  -> {
            lookUpViewModel.setPostLookupStateToInitial()
        }
        is  PostLookupUIState.Error -> {
            showSnackBar(coroutineScope,snackState,uiState.message)
            lookUpViewModel.setPostLookupStateToInitial()
        }

        is PostLookupUIState.GotData -> {
            lookUpViewModel.setPostLookupStateToInitial()
        }
        else -> {}
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp))
        {

            items(items = lookUpViewModel.userPost) {
                PostItem(it)
            }
        }
        if (uiState == PostLookupUIState.NoData) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.post_not_found))
            }
        }
        if (uiState == PostLookupUIState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ShowProgress(strokeThickNess = 3.dp, indicatorWidth = 30.dp)
            }
        }
    }
    LaunchedEffect(Unit){
        lookUpViewModel.userData?.let { lookUpViewModel.fetchUserPosts(it.userid) }
    }
    SnackBar(snackState)
}


@Composable
fun PostItem(post: UserPost) {
    Surface(
        color = primaryBaseColor,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
        ) {
            Text(text = post.title, color = primaryTextColor, fontWeight = FontWeight.Bold)
            Divider(color = primaryTextColor, thickness = 2.dp, modifier = Modifier.padding(vertical = 5.dp))
            Text(post.body, color = primaryTextColor)
        }
    }
}