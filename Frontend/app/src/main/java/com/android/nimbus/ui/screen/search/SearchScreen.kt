package com.android.nimbus.ui.screen.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.model.Article
import com.android.nimbus.ui.component.LeftAlignSubHeadline
import com.android.nimbus.ui.viewmodel.SharedViewModel

// TODO: Fix the underline and clipping of text in the search bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = SharedViewModel

    var searchText by rememberSaveable { mutableStateOf("") }
    var searchResults by rememberSaveable {
        mutableStateOf<List<Article>>(emptyList())
    }

    var searchActive by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            SearchBar(
                query = searchText,
                onQueryChange = { newText ->
                    searchText = newText
                    searchResults = viewModel.searchArticles(newText)
                },
                onSearch = {
                    searchResults = viewModel.searchArticles(searchText)
                },
                active = searchActive,
                onActiveChange = {
                    searchActive = it
                },
                modifier = modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Search",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color.Transparent,
                    dividerColor = MaterialTheme.colorScheme.primary,
                ),
                leadingIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Button",
                            modifier = modifier.size(30.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
            ) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(searchResults.size) { index ->
                        LeftAlignSubHeadline(
                            article = searchResults[index],
                            modifier = modifier,
                            action = {
                                viewModel.openFeeds(
                                    navController,
                                    searchResults[index].id,
                                    searchResults[index].category ?: ""
                                )
                            }
                        )
                        if (index < searchResults.size - 1) {
                            HorizontalDivider(
                                modifier = modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Nothing to show",
                modifier = modifier,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}