package com.android.nimbus.ui.screens.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.nimbus.model.Article
import com.android.nimbus.ui.components.SettingsAppBar
import com.android.nimbus.ui.components.Shimmer
import com.android.nimbus.ui.viewmodel.SharedViewModel
import com.android.nimbus.utility.bounceClick
import kotlinx.coroutines.launch

@Composable
fun BookmarksScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val viewModel = BookmarksViewModel()

    var bookmarks by rememberSaveable {
        mutableStateOf<List<Article>>(emptyList())
    }
    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            bookmarks = viewModel.getBookmarks()
            isLoading = false

            SharedViewModel.appendBookmarks(bookmarks)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            SettingsAppBar(
                title = "Bookmarks",
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(50.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                if (bookmarks.isNotEmpty()) {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(bookmarks.size) { index ->
                            BookmarkResult(
                                navController = navController,
                                viewModel = SharedViewModel,
                                article = bookmarks[index],
                                modifier = modifier
                            )
                            if (index < bookmarks.size - 1) {
                                HorizontalDivider(
                                    modifier = modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No Bookmarks",
                        modifier = modifier,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkResult(
    navController: NavController,
    viewModel: SharedViewModel,
    article: Article,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .bounceClick {
                viewModel
                    .openFeeds(navController, article.id, "bookmarks")
            }
    ) {
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.weight(1f),
            textAlign = TextAlign.Left
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