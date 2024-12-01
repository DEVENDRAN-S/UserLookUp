package com.example.userlookup.lookupfeature.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import com.example.userlookup.R
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.userlookup.lookupfeature.presentation.UserLookUpViewModel
import com.example.userlookup.lookupfeature.presentation.util.UserTabs
import com.example.userlookup.ui.theme.primaryBaseColor
import com.example.userlookup.ui.theme.primaryButtomColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserDetailBaseScreen(lookUpViewModel:UserLookUpViewModel)
{

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState (pageCount = { UserTabs.values().size})
    val selectedPageIndex = remember { derivedStateOf { pagerState.currentPage }}
    Scaffold(
        topBar = {
            TopAppBar(
            title = {  Text(text = stringResource(id = R.string.user_information))},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primaryButtomColor,
                    titleContentColor = Color.White,
                )

            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            TabRow(
                selectedTabIndex = selectedPageIndex.value,
                modifier = Modifier.fillMaxWidth(),
                containerColor = primaryButtomColor,
                contentColor = Color.White
                ) {
                UserTabs.values().forEachIndexed {index,currentab ->
                    Tab(
                        selected = selectedPageIndex.value == index,
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White,
                        text = {Text(text =currentab.name)},
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(currentab.ordinal)
                            }
                        }
                    )
                }

            }
            HorizontalPager(state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false
            ) {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    when (selectedPageIndex.value) {
                        UserTabs.DETAIL.ordinal -> UserDetailScreen(lookUpViewModel)

                        UserTabs.PAGE.ordinal -> UserPageScreen(lookUpViewModel)
                    }
                }

            }
        }

    }
}