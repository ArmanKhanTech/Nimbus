package com.android.nimbus.ui.screen.home.screen

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.android.nimbus.R
import com.android.nimbus.Screen
import com.android.nimbus.model.Articles
import com.android.nimbus.model.NewsModel
import com.android.nimbus.ui.components.CenterAlignedTopAppBar
import com.android.nimbus.ui.components.Shimmer
import com.android.nimbus.ui.screen.home.HomeViewModel
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

    val application = LocalContext.current.applicationContext as Application
    val applicationInfo: ApplicationInfo = application.packageManager
        .getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)
    val apiKey = applicationInfo.metaData.getString("NEWS_API_KEY")

    val viewModel = apiKey?.let {
        HomeViewModel(it)
    }

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
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Header(sheetState, scope, modifier)
                if (isDarkMode != null) {
                    FeatureRow(isDarkMode, navController, modifier)
                }
                TitleButton(
                    title = "Top Stories",
                    onButtonClick = {
                        // Handle top stories
                    }
                )
                TopStories(
                    viewModel = viewModel ?: HomeViewModel(""),
                    modifier
                )
                TopicsHeader(modifier)
                Topics(
                    viewModel = viewModel ?: HomeViewModel(""),
                    modifier
                )
                TitleButton(
                    title = "Recent",
                    onButtonClick = {
                        // Handle recent stories
                    }
                )
                Recent(
                    viewModel = viewModel ?: HomeViewModel(""),
                    modifier = modifier
                )
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
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        item {
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    navController.navigate(Screen.FEED.name)
                },
                imageDark = R.drawable.feed_icon_dark,
                imageLight = R.drawable.feed_icon_light,
                title = "Recent",
                modifier = modifier
            )
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    // Handle feature button
                },
                imageDark = R.drawable.news_icon_dark,
                imageLight = R.drawable.news_icon_light,
                title = "Top Stories"
            )
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    // Handle feature button
                },
                imageDark = R.drawable.stories_icon_dark,
                imageLight = R.drawable.stories_icon_light,
                title = "Top Stories"
            )
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    // Handle feature button
                },
                imageDark = R.drawable.trending_icon_dark,
                imageLight = R.drawable.trending_icon_light,
                title = "Trending"
            )
            FeatureButton(
                isDarkMode = isDarkMode,
                onButtonClick = {
                    // Handle feature button
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
    isDarkMode: MutableState<Boolean>,
    onButtonClick: () -> Unit,
    imageDark: Int,
    imageLight: Int,
    title: String,
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
                    .padding(1.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
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
                style = MaterialTheme.typography.titleMedium,
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
                modifier = modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TopStories(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    var topStories by remember {
        mutableStateOf<NewsModel?>(null)
    }

    LaunchedEffect(Unit) {
        topStories = viewModel.fetchTopStories()
    }

    Column(
        modifier = modifier.padding(18.dp, 0.dp, 18.dp, 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopStoriesMainHeadline(
            article = topStories?.articles?.get(0) ?: Articles(),
            modifier = modifier
        )
        CustomDivider(modifier)
        TopStoriesSubHeadlines(
            article = topStories?.articles?.get(1) ?: Articles(),
            modifier = modifier
        )
        CustomDivider(modifier)
        TopStoriesSubHeadlines(
            article = topStories?.articles?.get(2) ?: Articles(),
            modifier = modifier
        )
        CustomDivider(modifier)
        TopStoriesSubHeadlines(
            article = topStories?.articles?.get(3) ?: Articles(),
            modifier = modifier
        )
    }
}

@Composable
fun TopStoriesMainHeadline(
    article: Articles,
    modifier: Modifier
) {
    SubcomposeAsyncImage(
        model = article.media,
        contentDescription = "Headline Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                Shimmer(true, 1000f)
            )
    )
    Text(
        text = article.title ?: "",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun TopStoriesSubHeadlines(
    article: Articles,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
                .weight(1f)
        )
        VerticalDivider(
            modifier = modifier.width(20.dp)
        )
        SubcomposeAsyncImage(
            model = article.media,
            contentDescription = "Headline Image",
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

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Topics(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val images = listOf(
        R.drawable.beauty,
        R.drawable.business,
        R.drawable.economics,
        R.drawable.energy,
        R.drawable.entertainment,
        R.drawable.finance,
        R.drawable.food,
        R.drawable.gaming,
        R.drawable.music,
        R.drawable.politics,
        R.drawable.science,
        R.drawable.sports,
        R.drawable.tech,
        R.drawable.travel,
        R.drawable.world
    )

    val titles = listOf(
        "Beauty",
        "Business",
        "Economics",
        "Energy",
        "Entertainment",
        "Finance",
        "Food",
        "Gaming",
        "Music",
        "Politics",
        "Science",
        "Sports",
        "Tech",
        "Travel",
        "World"
    )

    val pagerState = rememberPagerState(pageCount = { titles.size })

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    var topics by remember {
        mutableStateOf<NewsModel?>(null)
    }

    val visited by remember {
        mutableStateOf<ArrayList<String>>(arrayListOf())
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (!visited.contains(titles[pagerState.currentPage])) {
            visited.add(titles[pagerState.currentPage])
            topics = viewModel.fetchTopics(titles[pagerState.currentPage])
        }
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
                                scope.launch {
                                    // fix animation
                                    pagerState.animateScrollToPage(index)
                                }
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
                    selected = pagerState.currentPage == index,
                    onClick = {
                        selectedTabIndex = index
                        scope.launch {
                            // fix animation
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        Spacer(modifier = modifier.height(20.dp))
        HorizontalPager(
            state = pagerState
        ) { page ->
            TopicsPage(
                page = page,
                pagerState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicsPage(
    page: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(18.dp, 0.dp)
    ) {
        TopicsSubHeadlines(
            modifier = modifier
        )
        CustomDivider(modifier)
        TopicsSubHeadlines(
            modifier = modifier
        )
        CustomDivider(modifier)
        TopicsSubHeadlines(
            modifier = modifier
        )
        Text(
            text = "View More",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .padding(0.dp, 20.dp)
                .clickable {
                    // Handle view more
                }
        )
    }
}

@Composable
fun TopicsSubHeadlines(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = null,
            contentDescription = "Headline Image",
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

            text = "Headline",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun Recent(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    var recent by remember {
        mutableStateOf<NewsModel?>(null)
    }

    LaunchedEffect(Unit) {
        recent = viewModel.fetchRecent()
    }

    Column(
        modifier = modifier.padding(18.dp, 0.dp, 18.dp, 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecentMainHeadline(
            article = recent?.articles?.get(0) ?: Articles(),
            modifier = modifier
        )
        CustomDivider(modifier)
        RecentSubHeadlines(
            article = recent?.articles?.get(1) ?: Articles(),
            modifier = modifier
        )
        CustomDivider(modifier)
        RecentSubHeadlines(
            article = recent?.articles?.get(2) ?: Articles(),
            modifier = modifier
        )
        CustomDivider(modifier)
        RecentSubHeadlines(
            article = recent?.articles?.get(3) ?: Articles(),
            modifier = modifier
        )
    }
}

@Composable
fun RecentMainHeadline(
    article: Articles,
    modifier: Modifier
) {
    SubcomposeAsyncImage(
        model = article.media,
        contentDescription = "Headline Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                Shimmer(true, 1000f)
            )
    )
    Text(
        text = article.title ?: "",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun RecentSubHeadlines(
    article: Articles,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        VerticalDivider(
            modifier = modifier.width(20.dp)
        )
        SubcomposeAsyncImage(
            model = article.media,
            contentDescription = "Headline Image",
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
