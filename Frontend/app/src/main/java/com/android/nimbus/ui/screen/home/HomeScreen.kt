package com.android.nimbus.ui.screen.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timelapse
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.android.nimbus.Screen
import com.android.nimbus.data.topics
import com.android.nimbus.data.topicsImages
import com.android.nimbus.model.Article
import com.android.nimbus.model.Day
import com.android.nimbus.ui.component.AutoScrollingText
import com.android.nimbus.ui.component.HomeAppBar
import com.android.nimbus.ui.component.LeftAlignSubHeadline
import com.android.nimbus.ui.component.RightAlignSubHeadline
import com.android.nimbus.ui.component.Shimmer
import com.android.nimbus.ui.viewmodel.SharedViewModel
import com.android.nimbus.utility.SharedPreferenceUtility
import com.android.nimbus.utility.bounceClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val sheetState = rememberModalBottomSheetState()

    val viewModel = SharedViewModel

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(navController, drawerState, scope, modifier)
        },
    ) {
        Scaffold(
            topBar = {
                HomeAppBar(
                    onMenuIconTap = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    onSearchIconTap = {
                        navController.navigate(Screen.SEARCH.name)
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Header(sheetState, scope, modifier)
                FeatureRow(navController, viewModel, modifier)
                TitleButton(
                    title = "Top Stories",
                    onButtonClick = {
                        viewModel.openFeeds(navController, null, "top_stories")
                    }, modifier
                )
                TopStories(navController, viewModel, modifier)
                TopicsHeader(modifier)
                Topics(navController, viewModel, modifier)
                TitleButton(
                    title = "Trending",
                    onButtonClick = {
                        viewModel.openFeeds(navController, null, "trending")
                    }, modifier
                )
                Trending(navController, viewModel, modifier)
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
    drawerState: DrawerState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val sharedPreferences = SharedPreferenceUtility(LocalContext.current)

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
                navController.navigate(
                    Screen.SETTINGS.name,
                    navOptions = navOptions {
                        popUpTo(Screen.HOME.name)
                    }
                )
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
                sharedPreferences.saveBooleanData("loggedIn", false)
                navController.navigate(Screen.LOGIN.name) {
                    launchSingleTop = true
                    popUpTo(Screen.HOME.name) {
                        inclusive = true
                    }
                }
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
    val context = LocalContext.current

    val city = SharedViewModel.city.collectAsState()

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
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = SharedViewModel.getCurrentDate(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = modifier.weight(1f))
        OutlinedButton(
            onClick = {
                scope.launch {
                    if (city.value != "Unknown" && city.value != "Permission not granted") {
                        sheetState.show()
                    } else {
                        Toast.makeText(context, "Please enable location", Toast.LENGTH_SHORT).show()
                    }
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
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun FeatureRow(
    navController: NavController,
    viewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        item {
            FeatureButton(
                title = "All News",
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Newspaper,
                        contentDescription = "All News Icon",
                        modifier = modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                onButtonClick = {
                    viewModel
                        .openFeeds(navController, null, "all_news")
                },
                modifier = modifier
            )
            FeatureButton(
                title = "Top Stories",
                icon = {
                    Icon(
                        imageVector = Icons.Filled.AutoStories,
                        contentDescription = "Top Stories Icon",
                        modifier = modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                onButtonClick = {
                    viewModel
                        .openFeeds(navController, null, "top_stories")
                },
                modifier = modifier
            )
            FeatureButton(
                title = "Trending",
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = "Trending Icon",
                        modifier = modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                onButtonClick = {
                    viewModel
                        .openFeeds(navController, null, "trending")
                },
                modifier = modifier,
            )
            FeatureButton(
                title = "Recent",
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Timelapse,
                        contentDescription = "Recent Icon",
                        modifier = modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                onButtonClick = {
                    viewModel
                        .openFeeds(navController, null, "recent")
                },
                modifier = modifier
            )
            FeatureButton(
                title = "Bookmarks",
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Bookmarks,
                        contentDescription = "Bookmarks Icon",
                        modifier = modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                onButtonClick = {
                    navController.navigate(Screen.BOOKMARKS.name)
                },
                modifier = modifier,
            )
        }
    }
}

@Composable
fun FeatureButton(
    title: String,
    icon: @Composable () -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onButtonClick,
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
            icon()
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier.padding(0.dp, 10.dp)
            )
        }
    }
}

@Composable
fun TitleButton(
    title: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onButtonClick,
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
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier.padding(
                    start = 18.dp,
                    top = 18.dp,
                    end = 10.dp,
                    bottom = 18.dp
                )
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Settings",
                modifier = modifier
                    .size(28.dp)
                    .padding(top = 2.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TopStories(
    navController: NavController,
    viewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    val topStories = viewModel
        .getArticlesByCategory("top_stories")

    Column(
        modifier = modifier.padding(18.dp, 0.dp, 18.dp, 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopStoriesMainHeadline(
            navController = navController,
            viewModel = viewModel,
            article = topStories[0],
            modifier = modifier
        )
        for (i in 1 until 4) {
            CustomDivider(modifier)
            RightAlignSubHeadline(
                article = topStories[i],
                action = {
                    viewModel
                        .openFeeds(navController, topStories[i].id, "top_stories")
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun TopStoriesMainHeadline(
    navController: NavController,
    viewModel: SharedViewModel,
    article: Article,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .bounceClick {
                viewModel
                    .openFeeds(navController, article.id, "top_stories")
            }
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = "Headline Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Shimmer(true, 1000f))
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Left
        )
    }
}

@Composable
fun TopicsHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Topics",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Topics(
    navController: NavController,
    viewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { topics.size })

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            topics.forEachIndexed { index, title ->
                Tab(
                    text = {
                        AutoScrollingText(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedTabIndex == index)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onBackground
                        )
                    },
                    icon = {
                        Column {
                            Image(
                                painter = painterResource(topicsImages[index]),
                                contentDescription = topics[index],
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .size(75.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .animateContentSize()
                                    .clickable {
                                        selectedTabIndex = index
                                    }
                                    .border(
                                        BorderStroke(
                                            width = if (selectedTabIndex == index) 2.dp else 0.dp,
                                            color = MaterialTheme.colorScheme.primary
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            Spacer(modifier = modifier.height(10.dp))
                        }
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        selectedTabIndex = index
                    },
                    modifier = modifier
                        .width(125.dp)
                )
            }
        }
        Spacer(modifier = modifier.height(10.dp))
        HorizontalPager(
            state = pagerState
        ) {
            TopicsPage(
                navController = navController,
                viewModel = viewModel,
                topic = topics[selectedTabIndex],
                modifier = modifier
            )
        }
    }
}

@Composable
fun TopicsPage(
    navController: NavController,
    viewModel: SharedViewModel,
    topic: String,
    modifier: Modifier = Modifier
) {
    val topics = viewModel
        .getArticlesByCategory(topic.lowercase())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(18.dp, 0.dp)
    ) {
        for (i in 0 until 4) {
            LeftAlignSubHeadline(
                article = topics[i],
                action = {
                    viewModel
                        .openFeeds(navController, topics[i].id, topic.lowercase())
                },
                modifier = modifier
            )
            if (i != 3) {
                CustomDivider(modifier)
            }
        }
        Text(
            text = "View More",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .padding(0.dp, 20.dp)
                .clickable {
                    viewModel.openFeeds(navController, null, topic.lowercase())
                }
        )
    }
}

@Composable
fun Trending(
    navController: NavController,
    viewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    val trending = SharedViewModel
        .getArticlesByCategory("trending")

    Column(
        modifier = modifier.padding(18.dp, 0.dp, 18.dp, 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TrendingMainHeadline(
            navController = navController,
            viewModel = viewModel,
            article = trending[0],
            modifier = modifier
        )
        for (i in 1 until 4) {
            CustomDivider(modifier)
            RightAlignSubHeadline(
                article = trending[i],
                action = {
                    viewModel
                        .openFeeds(navController, trending[i].id, "trending")
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun TrendingMainHeadline(
    navController: NavController,
    viewModel: SharedViewModel,
    article: Article,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .bounceClick {
                viewModel
                    .openFeeds(navController, article.id, "trending")
            }
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = "Headline Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Shimmer(true, 1000f))
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = article.title ?:"",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Left
        )
    }
}

@Composable
fun CustomDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        thickness = 0.15.dp,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(10.dp, 0.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    val weather = SharedViewModel.weather.collectAsState()
    val city = SharedViewModel.city.collectAsState()

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
                        text = weather.value.day[0].temperature.toString() + "℃",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = city.value ?: "City",
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
                items(3) {
                    WeatherCard(
                        weather = weather.value.day[it],
                        modifier = modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherCard(
    weather: Day,
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = "Day")
//        Image(
//            painter = ,
//            contentDescription =
//        )
        Text(text = "38℃")
    }
}
