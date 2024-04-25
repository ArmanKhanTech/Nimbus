package com.android.nimbus.ui.screen.feed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.nimbus.R
import com.android.nimbus.ui.components.Shimmer
import com.android.nimbus.ui.components.TopAppBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(
    navController: NavController,
    isDarkMode: MutableState<Boolean>?,
    title: String,
    modifier: Modifier = Modifier
) {
    var pageCount by remember {
        mutableIntStateOf(5)
    }
    var pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = title,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        },
    ) { innerPadding ->
        VerticalPager(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = pagerState,
            beyondBoundsPageCount = 5,
        ) {
            DisplayNews(isDarkMode, modifier)
        }

        LaunchedEffect(pagerState.currentPage) {
            val currentPage = pagerState.currentPage
            if (currentPage % 5 == 0) {
                pageCount += 5
            }
        }
    }
}

@Composable
fun DisplayNews(
    isDarkMode: MutableState<Boolean>?,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp

    Column(
        modifier = modifier
            .height(screenHeight)
    ) {
        AsyncImage(
            model = null,
            contentDescription = "News Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(screenHeight / 3f)
                .background(
                    Shimmer(true, 1000f)
                )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Headline",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(horizontal = 16.dp),
            maxLines = 3
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Headline",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(horizontal = 16.dp),
            maxLines = 3
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = "Author",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(horizontal = 16.dp),
            maxLines = 3
        )
        Spacer(modifier = modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    // handle bookmark
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(8.dp),
                shape = CircleShape,
                modifier = modifier.size(40.dp)
            ) {
                if (isDarkMode != null) {
                    Image(
                        if (isDarkMode.value) painterResource(R.drawable.bookmark_icon_dark)
                        else painterResource(R.drawable.bookmark_icon_light),
                        contentDescription = "Bookmark",
                        contentScale = ContentScale.FillHeight,
                        modifier = modifier
                            .size(30.dp)
                            .padding(1.dp)
                    )
                }
            }
            Spacer(modifier = modifier.width(10.dp))
            OutlinedButton(
                onClick = {
                    // handle share
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(10.dp),
                shape = CircleShape,
                modifier = modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Settings",
                    modifier = modifier.size(26.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}