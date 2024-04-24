package com.android.nimbus.ui.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SheetState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.android.nimbus.R
import com.android.nimbus.Screen
import com.android.nimbus.ui.components.CenterAlignedTopAppBar
import com.android.nimbus.ui.components.Shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    isDarkMode: MutableState<Boolean>?,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sheetState = rememberModalBottomSheetState()
    val scope = CoroutineScope(
        context = rememberCoroutineScope().coroutineContext
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(navController, scope, drawerState, modifier)
        },
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    onMenuIconTap = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = modifier.padding(innerPadding)
            ) {
                item {
                    Header(sheetState, scope, modifier)
                    if (isDarkMode != null) {
                        FeatureRow(isDarkMode, modifier)
                    }
                    TopStoriesButton(modifier)
                    TopStories(modifier)
                    TopicsHeader(modifier)
                    Topics(modifier)
                }
            }

            if (sheetState.isVisible) {
                BottomSheet(sheetState, scope, modifier)
            }
        }
    }
}

@Composable
fun Drawer(
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        drawerShape = RectangleShape,
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerTonalElevation = 0.dp
    ) {
        Text(
            "Menu",
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary
        )
        NavigationDrawerItem(
            modifier = modifier.padding(10.dp, 20.dp, 10.dp),
            label = {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate(Screen.SETTINGS.name)
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    modifier = modifier.size(26.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        NavigationDrawerItem(
            modifier = modifier.padding(10.dp, 0.dp),
            label = {
                Text(
                    text = "Log-Out",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            selected = false,
            onClick = {
                // Handle log-out
                navController.navigate(Screen.LOGIN.name)
            },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Settings",
                    modifier = modifier.size(26.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    sheetState: SheetState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your Briefing",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Tuesday, 20 July",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = modifier.weight(1f))
        OutlinedButton(
            onClick = {
                scope.launch {
                    sheetState.show()
                }
            },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "38\u2103",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun FeatureRow(
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        item {
            OutlinedButton(
                onClick = {
                    // Handle my feed
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(18.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        if (isDarkMode.value) painterResource(R.drawable.feed_icon_dark)
                        else painterResource(R.drawable.feed_icon_light),
                        contentDescription = "My Feed",
                        contentScale = ContentScale.Crop,
                        modifier = modifier.size(50.dp)
                    )
                    Text(
                        text = "My Feed",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier.padding(0.dp, 10.dp)
                    )
                }
            }
            OutlinedButton(
                onClick = {
                    // Handle all news
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(18.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        if (isDarkMode.value) painterResource(R.drawable.news_icon_dark)
                        else painterResource(R.drawable.news_icon_light),
                        contentDescription = "All News",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(50.dp)
                            .padding(2.dp)
                    )
                    Text(
                        text = "All News",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier.padding(0.dp, 10.dp)
                    )
                }
            }
            OutlinedButton(
                onClick = {
                    // Handle top stories
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(18.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        if (isDarkMode.value) painterResource(R.drawable.stories_icon_dark)
                        else painterResource(R.drawable.stories_icon_light),
                        contentDescription = "Top Stories",
                        contentScale = ContentScale.FillHeight,
                        modifier = modifier.size(50.dp)
                    )
                    Text(
                        text = "Top Stories",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier.padding(0.dp, 10.dp)
                    )
                }
            }
            OutlinedButton(
                onClick = {
                    // Handle trending
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(18.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        if (isDarkMode.value) painterResource(R.drawable.trending_icon_dark)
                        else painterResource(R.drawable.trending_icon_light),
                        contentDescription = "Trending",
                        contentScale = ContentScale.FillHeight,
                        modifier = modifier.size(50.dp)
                    )
                    Text(
                        text = "Trending",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier.padding(0.dp, 10.dp)
                    )
                }
            }
            OutlinedButton(
                onClick = {
                    // Handle bookmarks
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(18.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        if (isDarkMode.value) painterResource(R.drawable.bookmark_icon_dark)
                        else painterResource(R.drawable.bookmark_icon_light),
                        contentDescription = "Bookmarks",
                        contentScale = ContentScale.FillHeight,
                        modifier = modifier
                            .size(50.dp)
                            .padding(1.dp)
                    )
                    Text(
                        text = "Bookmarks",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier.padding(0.dp, 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopStoriesButton(
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = {
            // Handle top stories
        },
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.background
        ),
        contentPadding = PaddingValues(0.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Stories",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier.padding(18.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Settings",
                modifier = modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TopStories(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(18.dp, 0.dp, 18.dp, 18.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            AsyncImage(
                model = null,
                contentDescription = "Top Stories Headline One Image",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Shimmer(true, 1000f)
                    )
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = "News Title",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "News Title",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = modifier.weight(1f))
                AsyncImage(
                    model = null,
                    contentDescription = "Top Stories Headline Two Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Shimmer(true, 1000f)
                        )
                )
            }
            Spacer(modifier = modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "News Title",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = modifier.weight(1f))
                AsyncImage(
                    model = null,
                    contentDescription = "Top Stories Headline Three Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Shimmer(true, 1000f)
                        )
                )
            }
            Spacer(modifier = modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "News Title",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = modifier.weight(1f))
                AsyncImage(
                    model = null,
                    contentDescription = "Top Stories Headline Four Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Shimmer(true, 1000f)
                        )
                )
            }
        }
    }
}

@Composable
fun TopicsHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Topics",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = modifier.weight(1f))
            ClickableText(
                text = AnnotatedString(
                    "See All",
                    spanStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ),
                onClick = {
                    // Handle see all
                },
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Topics(
    modifier: Modifier = Modifier
) {
    val images = listOf(
        R.drawable.arts,
        R.drawable.business,
        R.drawable.climate,
        R.drawable.economy,
        R.drawable.education,
        R.drawable.fashion,
        R.drawable.health,
        R.drawable.jobs,
        R.drawable.movie,
        R.drawable.music,
        R.drawable.politics,
        R.drawable.science,
        R.drawable.sports,
        R.drawable.space,
        R.drawable.technology,
        R.drawable.travel,
    )

    val titles = listOf(
        "Arts",
        "Business",
        "Climate",
        "Economy",
        "Education",
        "Fashion",
        "Health",
        "Jobs",
        "Movie",
        "Music",
        "Politics",
        "Science",
        "Sports",
        "Space",
        "Technology",
        "Travel",
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(18.dp, 0.dp)
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = {},
            divider = {}
        ) {
            images.forEachIndexed { index, _ ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(images[index]),
                        contentDescription = titles[index],
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(if (selectedTabIndex == index) 75.dp else 60.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .animateContentSize()
                            .clickable {
                                selectedTabIndex = index
                            }
                            .border(
                                BorderStroke(
                                    width = if (selectedTabIndex == index) 2.dp else 0.25.dp,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Spacer(modifier = modifier.weight(1f))
                }
            }
        }
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            style = if (selectedTabIndex == index) MaterialTheme.typography.bodyMedium
                            else MaterialTheme.typography.bodySmall,
                            color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    }
                )
            }
        }
        Spacer(modifier = modifier.height(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = null,
                    contentDescription = "Topic Headline One Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Shimmer(true, 1000f)
                        )
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "News Title",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(modifier = modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = null,
                    contentDescription = "Topic Headline Two Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Shimmer(true, 1000f)
                        )
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "News Title",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(modifier = modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = null,
                    contentDescription = "Topic Headline Three Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Shimmer(true, 1000f)
                        )
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "News Title",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(modifier = modifier.height(30.dp))
            Text(
                text = "View More",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = modifier.height(30.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = modifier.padding(20.dp)
        ) {
            Row {
                Column {
                    Text(
                        text = "38℃",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "City",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                // Image(painter = , contentDescription = )
            }
            Spacer(modifier = modifier.weight(1f))
            LazyRow(
                modifier = modifier.padding(20.dp)
            ) {
                items(5) {
                    // TempCard(modifier)
                }
            }
        }
    }
}

//@Composable
//fun TempCard(
//    modifier: Modifier = Modifier
//) {
//    Column {
//        Text(text = "Day")
//        Image(painter = , contentDescription = )
//        Text(text = "38℃")
//    }
//}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
        isDarkMode = null,
        modifier = Modifier
    )
}