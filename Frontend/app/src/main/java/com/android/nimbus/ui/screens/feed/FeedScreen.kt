package com.android.nimbus.ui.screen.feed

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.nimbus.R
import com.android.nimbus.model.Article
import com.android.nimbus.ui.components.CustomToast
import com.android.nimbus.ui.components.FeedsAppBar
import com.android.nimbus.ui.components.MessageType
import com.android.nimbus.ui.components.Shimmer
import com.android.nimbus.ui.screens.bookmarks.BookmarksViewModel
import com.android.nimbus.ui.viewmodel.SharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(
    navController: NavController,
    articleID: String?,
    category: String,
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var feed by remember {
        mutableStateOf(
            SharedViewModel.getArticlesByCategory(category)
        )
    }

    var currentArticle: Article
    LaunchedEffect(Unit) {
        if (articleID != null) {
            currentArticle = SharedViewModel.getArticleByID(articleID)
            feed.add(0, currentArticle)
            feed = feed.distinct() as ArrayList<Article>
        }
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { feed.size }
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            FeedsAppBar(
                title = when (category) {
                    "all_news" -> "All News"
                    "top_stories" -> "Top Stories"
                    "trending" -> "Trending"
                    "bookmarks" -> "Bookmarks"
                    else -> category.replaceFirstChar {
                        if (it.isLowerCase())
                            it.titlecase(Locale.getDefault())
                        else
                            it.toString()
                    }
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        VerticalPager(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = pagerState
        ) {
            DisplayNews(
                article = feed[it],
                pagerState = pagerState,
                context = context,
                isDarkMode = isDarkMode,
                modifier = modifier
            )
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayNews(
    article: Article,
    pagerState: PagerState,
    context: Context,
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val scrollState = rememberScrollState()

    var saved by remember { mutableStateOf(false) }
    var saving by remember { mutableStateOf(false) }

    var showToast by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .height(screenHeight)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = "News Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .height(screenHeight / 3f)
                .background(Shimmer(true, 1000f))
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (showToast) {
            CustomToast(
                message = if (saved) "Article saved successfully"
                else "Article could not be saved",
                messageType = if (saved) MessageType.SUCCESS
                else MessageType.ERROR,
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.content ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .weight(6f)
                .verticalScroll(state = scrollState)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                ) {
                    scope.launch {
                        if (scrollState.value == scrollState.maxValue) {
                            scrollState.animateScrollTo(0)
                        } else {
                            scrollState.animateScrollTo(scrollState.maxValue)
                        }
                    }
                },
            textAlign = TextAlign.Left
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = ("Author: " + article.author),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp)
        ) {
            if (saving) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = modifier.size(30.dp)
                )
            } else {
                ActionButton(
                    title = "Bookmark",
                    padding = PaddingValues(2.dp),
                    size = 30,
                    darkIcon = R.drawable.bookmark_icon_dark,
                    lightIcon = R.drawable.bookmark_icon_light,
                    onButtonClick = {
                        val viewModel = BookmarksViewModel()
                        scope.launch {
                            if (!saved) {
                                saving = true
                                saved = viewModel.addBookmark(article)
                                showToast = true

                                saving = false
                                delay(2000)
                                showToast = false
                            }
                        }
                    },
                    isDarkMode = isDarkMode
                )
            }
            Spacer(modifier = modifier.width(10.dp))
            ActionButton(
                title = "Share",
                padding = PaddingValues(2.dp),
                size = 30,
                darkIcon = R.drawable.share_icon_dark,
                lightIcon = R.drawable.share_icon_light,
                onButtonClick = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, article.sourceUrl)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(context, shareIntent, null)
                },
                isDarkMode = isDarkMode
            )
            Spacer(modifier = modifier.width(10.dp))
            ActionButton(
                title = "Top",
                padding = PaddingValues(1.dp),
                size = 30,
                darkIcon = R.drawable.top_icon_dark,
                lightIcon = R.drawable.top_icon_light,
                onButtonClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                isDarkMode = isDarkMode
            )
            Spacer(modifier = modifier.width(10.dp))
            ActionButton(
                title = "Web",
                padding = PaddingValues(0.dp),
                size = 30,
                darkIcon = R.drawable.web_icon_dark,
                lightIcon = R.drawable.web_icon_light,
                onButtonClick = {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        android.net.Uri.parse(article.sourceUrl)
                    )
                    startActivity(context, browserIntent, null)
                },
                isDarkMode = isDarkMode
            )
        }
    }
}

@Composable
fun ActionButton(
    title: String,
    padding: PaddingValues,
    size: Int,
    onButtonClick: () -> Unit,
    darkIcon: Int,
    lightIcon: Int,
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
            color = MaterialTheme.colorScheme.primary
        ),
        contentPadding = PaddingValues(8.dp),
        shape = CircleShape,
        modifier = modifier.size(40.dp)
    ) {
        Image(
            if (isDarkMode.value) painterResource(darkIcon)
            else painterResource(lightIcon),
            contentDescription = title,
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .size(size.dp)
                .padding(padding)
        )
    }
}