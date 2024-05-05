package com.android.nimbus.ui.screen.home

import android.annotation.SuppressLint
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.android.nimbus.R
import com.android.nimbus.Screen
import com.android.nimbus.data.topics
import com.android.nimbus.data.topicsImages
import com.android.nimbus.model.Article
import com.android.nimbus.ui.components.CenterAlignedTopAppBar
import com.android.nimbus.ui.components.Shimmer
import com.android.nimbus.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ViewModel,
    isDarkMode: MutableState<Boolean>?,
    modifier: Modifier = Modifier
) {
    val scope = CoroutineScope(context = rememberCoroutineScope().coroutineContext)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sheetState = rememberModalBottomSheetState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(navController, drawerState, scope, modifier)
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
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Header(sheetState, scope, viewModel, modifier)
                if (isDarkMode != null) {
                    FeatureRow(navController, viewModel, isDarkMode, modifier)
                }
                TitleButton(
                    title = "Top Stories",
                    onButtonClick = {
                        viewModel.feedsTitle = "Top Stories"
                        navController.navigate(
                            Screen.FEED.name,
                            navOptions = navOptions {
                                popUpTo(Screen.HOME.name)
                            }
                        )
                    }, modifier
                )
                TopStories(navController, viewModel, modifier)
                TopicsHeader(modifier)
                Topics(navController, viewModel, modifier)
                TitleButton(
                    title = "Trending",
                    onButtonClick = {
                        viewModel.feedsTitle = "Trending"
                        navController.navigate(
                            Screen.FEED.name,
                            navOptions = navOptions {
                                popUpTo(Screen.HOME.name)
                            }
                        )
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
    viewModel: ViewModel,
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
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = viewModel.getCurrentDate(),
                style = MaterialTheme.typography.bodyMedium,
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
    viewModel: ViewModel,
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        item {
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    viewModel.feedsTitle = "All News"
                    navController.navigate(
                        Screen.FEED.name,
                        navOptions = navOptions {
                            popUpTo(Screen.HOME.name)
                        }
                    )
                },
                imageDark = R.drawable.feed_icon_dark,
                imageLight = R.drawable.feed_icon_light,
                title = "All News",
                modifier = modifier
            )
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    viewModel.feedsTitle = "Top Stories"
                    navController.navigate(
                        Screen.FEED.name,
                        navOptions = navOptions {
                            popUpTo(Screen.HOME.name)
                        }
                    )
                },
                imageDark = R.drawable.news_icon_dark,
                imageLight = R.drawable.news_icon_light,
                title = "Top Stories"
            )
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    viewModel.feedsTitle = "Trending"
                    navController.navigate(
                        Screen.FEED.name,
                        navOptions = navOptions {
                            popUpTo(Screen.HOME.name)
                        }
                    )
                },
                imageDark = R.drawable.trending_icon_dark,
                imageLight = R.drawable.trending_icon_light,
                title = "Trending"
            )
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    viewModel.feedsTitle = "Bookmarks"
                    navController.navigate(
                        Screen.FEED.name,
                        navOptions = navOptions {
                            popUpTo(Screen.HOME.name)
                        }
                    )
                },
                imageDark = R.drawable.bookmark_icon_dark,
                imageLight = R.drawable.bookmark_icon_light,
                title = "Bookmarks"
            )
        }
    }
}

@Composable
fun FeatureButton(
    onButtonClick: () -> Unit,
    imageDark: Int,
    imageLight: Int,
    title: String,
    isDarkMode: MutableState<Boolean>,
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
            Image(
                if (isDarkMode.value) painterResource(imageDark)
                else painterResource(imageLight),
                contentDescription = title,
                contentScale = ContentScale.FillHeight,
                modifier = modifier
                    .size(50.dp)
                    .padding(if (title == "Top Stories") 2.dp else 1.dp)
            )
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
    viewModel: ViewModel,
    modifier: Modifier = Modifier
) {
    val topStories = viewModel.getArticlesByCategory("top_stories")

    Column(
        modifier = modifier.padding(18.dp, 0.dp, 18.dp, 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopStoriesMainHeadline(
            article = topStories.articles[0],
            viewModel = viewModel,
            navController = navController,
            modifier = modifier
        )
        CustomDivider(modifier)
        TopStoriesSubHeadlines(
            article = topStories.articles[1],
            viewModel = viewModel,
            navController = navController,
            modifier = modifier
        )
        CustomDivider(modifier)
        TopStoriesSubHeadlines(
            article = topStories.articles[2],
            viewModel = viewModel,
            navController = navController,
            modifier = modifier
        )
        CustomDivider(modifier)
        TopStoriesSubHeadlines(
            article = topStories.articles[3],
            viewModel = viewModel,
            navController = navController,
            modifier = modifier
        )
    }
}

@Composable
fun TopStoriesMainHeadline(
    article: Article,
    navController: NavController,
    viewModel: ViewModel,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable {
            viewModel.feedsTitle = "Top Stories"
            viewModel.currentArticleInFeed = article.id
            navController.navigate(
                Screen.FEED.name,
                navOptions = navOptions {
                    popUpTo(Screen.HOME.name)
                }
            )
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
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun TopStoriesSubHeadlines(
    article: Article,
    navController: NavController,
    viewModel: ViewModel,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.clickable {
            viewModel.feedsTitle = "Top Stories"
            viewModel.currentArticleInFeed = article.id
            navController.navigate(
                Screen.FEED.name,
                navOptions = navOptions {
                    popUpTo(Screen.HOME.name)
                }
            )
        }
    ) {
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.weight(1f)
        )
        VerticalDivider(
            modifier = modifier.width(10.dp)
        )
        AsyncImage(
            model = article.imageUrl,
            contentDescription = "Headline Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(75.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Shimmer(true, 1000f))
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
    viewModel: ViewModel,
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
            indicator = {},
            divider = {}
        ) {
            topicsImages.forEachIndexed { index, _ ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(topicsImages[index]),
                        contentDescription = topics[index],
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(if (selectedTabIndex == index) 80.dp else 60.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .animateContentSize()
                            .clickable {
                                selectedTabIndex = index
                            }
                            .border(
                                BorderStroke(
                                    width = if (selectedTabIndex == index) 2.dp else 0.5.dp,
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
            selectedTabIndex = selectedTabIndex,
            divider = {},
        ) {
            topics.forEachIndexed { index, title ->
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
                    selected = pagerState.currentPage == index,
                    onClick = {
                        selectedTabIndex = index
                    }
                )
            }
        }
        Spacer(modifier = modifier.height(20.dp))
        HorizontalPager(
            state = pagerState
        ) {
            TopicsPage(
                topic = topics[selectedTabIndex],
                viewModel = viewModel,
                navController = navController,
                modifier = modifier
            )
        }
    }
}

@Composable
fun TopicsPage(
    topic: String,
    viewModel: ViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val topics = viewModel.getArticlesByCategory(topic.lowercase())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(18.dp, 0.dp)
    ) {
        TopicsSubHeadlines(
            article = topics.articles[0],
            viewModel = viewModel,
            navController = navController,
            topic = topic,
            modifier = modifier
        )
        CustomDivider(modifier)
        TopicsSubHeadlines(
            article = topics.articles[1],
            viewModel = viewModel,
            navController = navController,
            topic = topic,
            modifier = modifier
        )
        CustomDivider(modifier)
        TopicsSubHeadlines(
            article = topics.articles[2],
            viewModel = viewModel,
            navController = navController,
            topic = topic,
            modifier = modifier
        )
        CustomDivider(modifier)
        TopicsSubHeadlines(
            article = topics.articles[3],
            viewModel = viewModel,
            navController = navController,
            topic = topic,
            modifier = modifier
        )
        Text(
            text = "View More",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .padding(0.dp, 20.dp)
                .clickable {
                    viewModel.feedsTitle = topic
                    navController.navigate(
                        Screen.FEED.name,
                        navOptions = navOptions {
                            popUpTo(Screen.HOME.name)
                        }
                    )
                }
        )
    }
}

@Composable
fun TopicsSubHeadlines(
    article: Article,
    navController: NavController,
    viewModel: ViewModel,
    topic: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.clickable {
            viewModel.feedsTitle = topic
            viewModel.currentArticleInFeed = article.id
            navController.navigate(
                Screen.FEED.name,
                navOptions = navOptions {
                    popUpTo(Screen.HOME.name)
                }
            )
        }
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = "Headline Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(75.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Shimmer(true, 1000f))
        )
        VerticalDivider(
            modifier = modifier.width(10.dp)
        )
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.weight(1f)
        )
    }
}

@Composable
fun Trending(
    navController: NavController,
    viewModel: ViewModel,
    modifier: Modifier = Modifier
) {
    val trending = viewModel.getArticlesByCategory("trending")

    Column(
        modifier = modifier.padding(18.dp, 0.dp, 18.dp, 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TrendingMainHeadline(
            article = trending.articles[0],
            viewModel = viewModel,
            navController = navController,
            modifier = modifier
        )
        CustomDivider(modifier)
        TrendingSubHeadlines(
            article = trending.articles[1],
            viewModel = viewModel,
            navController = navController,
            modifier = modifier
        )
        CustomDivider(modifier)
        TrendingSubHeadlines(
            article = trending.articles[2],
            viewModel = viewModel,
            navController = navController,
            modifier = modifier
        )
        CustomDivider(modifier)
        TrendingSubHeadlines(
            article = trending.articles[3],
            viewModel = viewModel,
            navController = navController,
            modifier = modifier
        )
    }
}

@Composable
fun TrendingMainHeadline(
    article: Article,
    navController: NavController,
    viewModel: ViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable {
            viewModel.feedsTitle = "Trending"
            viewModel.currentArticleInFeed = article.id
            navController.navigate(
                Screen.FEED.name,
                navOptions = navOptions {
                    popUpTo(Screen.HOME.name)
                }
            )
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
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun TrendingSubHeadlines(
    article: Article,
    navController: NavController,
    viewModel: ViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.clickable {
            viewModel.feedsTitle = "Trending"
            viewModel.currentArticleInFeed = article.id
            navController.navigate(
                Screen.FEED.name,
                navOptions = navOptions {
                    popUpTo(Screen.HOME.name)
                }
            )
        }
    ) {
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.weight(1f)
        )
        VerticalDivider(
            modifier = modifier.width(10.dp)
        )
        AsyncImage(
            model = article.imageUrl,
            contentDescription = "Headline Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(75.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Shimmer(true, 1000f))
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
