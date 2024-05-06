package com.android.nimbus.ui.screen.feeds

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.nimbus.R
import com.android.nimbus.model.Article
import com.android.nimbus.model.NewsModel
import com.android.nimbus.ui.components.FeedsAppBar
import com.android.nimbus.ui.components.Shimmer
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(
    navController: NavController,
    newsModel: NewsModel,
    category: String,
    articleID: String?,
    isDarkMode: MutableState<Boolean>?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val viewModel = FeedsViewModel(
        newsModel = newsModel,
        category = category,
        articleID = articleID,
    )

    val feed by remember {
        mutableStateOf( viewModel.getArticlesByCategory(
            when (viewModel.category) {
                "All News" -> "all_news"
                "Top Stories" -> "top_stories"
                else -> viewModel.category.lowercase()
            }
        ))
    }

    var currentArticle: NewsModel? = null
    LaunchedEffect(viewModel.articleID) {
        if(viewModel.articleID != null) {
            currentArticle = viewModel.getArticleByID(viewModel.articleID)
            feed.articles.add(0, currentArticle!!.articles[0])
            feed.articles = feed.articles.distinct() as ArrayList<Article>
        }
    }

    var pageCount by rememberSaveable { mutableIntStateOf(5) }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            FeedsAppBar(
                title = viewModel.category,
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
            state = pagerState,
            beyondBoundsPageCount = 3,
        ) {
            if (currentArticle != null) {
                DisplayNews(
                    currentArticle!!.articles[it],
                    pagerState,
                    viewModel,
                    context,
                    isDarkMode!!,
                    modifier
                )
            } else {
                DisplayNews(
                    feed.articles[it],
                    pagerState,
                    viewModel,
                    context,
                    isDarkMode!!,
                    modifier
                )
            }
        }
        LaunchedEffect(pagerState.currentPage) {
            val currentPage = pagerState.currentPage
            if (currentPage % 3 == 0) {
                pageCount += 3
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayNews(
    article: Article,
    pagerState: PagerState,
    viewModel: FeedsViewModel,
    context: Context,
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        modifier = modifier
            .height(screenHeight)
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = "News Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(screenHeight / 3f)
                .background(Shimmer(true, 1000f))
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.content ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(state = rememberScrollState())
                .weight(5f)
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = (" - " + article.author),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(horizontal = 16.dp),
            maxLines = 1
        )
        Spacer(modifier = modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp)
        ) {
            ActionButton(
                title = "Bookmark",
                padding = PaddingValues(2.dp),
                size = 30,
                darkIcon = R.drawable.bookmark_icon_dark,
                lightIcon = R.drawable.bookmark_icon_light,
                onButtonClick = {
                    // Add bookmark functionality here
                },
                isDarkMode = isDarkMode
            )
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