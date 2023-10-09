package com.starwars.app

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.starwars.app.character.presentation.CharacterScreen
import com.starwars.app.starship.presentation.StarshipScreen
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController
) {
    TabBar(navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabBar(
    navController: NavController
) {

    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()

    val tabs = listOf("Character", "Starship", "Planet")

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Color.White,
                    modifier = Modifier.tabIndicatorOffset(
                        tabPositions[pagerState.currentPage]
                    )
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = { Text(text = title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            when (page) {
                0 -> CharacterScreen(navController = navController)
                1 -> StarshipScreen(navController = navController)
                2 -> TabContent("Tab 3 Content")
                else -> TabContent("Unknown Tab Content")
            }
        }
    }
}

@Composable
fun TabContent(content: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = content)
    }
}

